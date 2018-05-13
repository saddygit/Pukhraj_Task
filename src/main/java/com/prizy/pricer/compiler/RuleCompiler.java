package com.prizy.pricer.compiler;

import com.prizy.pricer.exception.NoRuleClassFoundException;
import com.prizy.pricer.rule.Rule;
import lombok.extern.slf4j.Slf4j;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * compiles rules written in java code and provides a convenient way to initialize instance
 */
 
public class RuleCompiler {

	
    private static Map<Class<? extends Rule>,Object> clazzMap = new HashMap<>();

    public static Object getDynamicClass(Class<? extends Rule> ruleClazz){

        if(clazzMap.get(ruleClazz) != null){
            return clazzMap.get(ruleClazz);
        }else {
            Object clazzObject = null;
            try {
                clazzObject = loadClass(ruleClazz);
                //clazzMap.put(ruleClazz, clazzObject); //todo uncomment
            } catch (ClassNotFoundException | IOException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return clazzObject;
        }
    }


    /**
     *
     * @param ruleClazz    type of the class/interface which the dynamic class inherits
     * @return instantiated object of the type of rule
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private static Object loadClass(Class<? extends Rule> ruleClazz) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        String ruleDirectory = bundle.getString("rule.directory");
        String ruleFilePath = ruleDirectory + File.separator + ruleClazz.getSimpleName() + "Impl.java";
        Object obj = null;
        File ruleFile = new File(ruleFilePath);
        System.setProperty("java.home", System.getenv("JAVA_HOME"));
        if (ruleFile.getParentFile().exists()) {
            try {
                DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

                // This sets up the class path that the compiler will use.
                // I've added the .jar file that contains the DoStuff interface within in it...
                List<String> optionList = new ArrayList<>();
                optionList.add("-classpath");

                String ruleClassPath = ruleClazz.getProtectionDomain().getCodeSource().getLocation().getPath().replaceFirst("file:","").replaceAll("!/", "");
                optionList.add(System.getProperty("java.class.path") + File.pathSeparator +ruleClassPath + File.pathSeparator + "." +File.pathSeparator);
                optionList.add("-d");
                optionList.add(new File(ruleClassPath).getParent());

                Iterable<? extends JavaFileObject> compilationUnit= fileManager.getJavaFileObjectsFromFiles(Arrays.asList(ruleFile));
                JavaCompiler.CompilationTask task = compiler.getTask(null,fileManager,diagnostics,optionList,null,compilationUnit);
                /********************************************************************************************* Compilation Requirements **/
                if (task.call()) {
                    /** Load and execute *************************************************************************************************/

                    // Create a new custom class loader, pointing to the directory that contains the compiled
                    // classes, this should point to the top of the package structure!
                    //URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("./").toURI().toURL()});
                    URLClassLoader classLoader = new URLClassLoader(new URL[]{new File(ruleClassPath).getParentFile().toURI().toURL()});
                    // Load the class from the classloader by name....
                    Class<?> loadedClass = classLoader.loadClass(ruleClazz.getSimpleName()+"Impl");
                    // Create a new instance...
                    obj = loadedClass.newInstance();
                } else {
                    for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                        log.error(String.format("Error on line %d in %s%n",diagnostic.getLineNumber(),diagnostic.getSource().toUri()));
                    }
                }
                fileManager.close();
            } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exp) {
                log.error("error while loading class ", exp);
                throw exp;
            }
        }
        if(obj == null){
            throw new NoRuleClassFoundException("No Rule class found for :" + ruleClazz.getName());
        }
        return obj;
    }

}