//package com.mezo.spring6reactivemongo.services.mezo;
//
//import org.springframework.stereotype.Component;
//
//import javax.annotation.processing.*;
//import javax.lang.model.SourceVersion;
//import javax.lang.model.element.AnnotationMirror;
//import javax.lang.model.element.Element;
//import javax.lang.model.element.ExecutableElement;
//import javax.lang.model.element.TypeElement;
//import java.util.List;
//import java.util.Set;
//
//@SupportedAnnotationTypes("RootServiceTestAnnotation")
//@Component
//public class RootServiceTestableInterfaceProcessor extends AbstractProcessor {
//
//    @Override
//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//
//        for (Element element : roundEnv.getElementsAnnotatedWith(RootServiceTestAnnotation.class)) {
//            System.out.println("*************");
//            System.out.println(element.getClass());
//            System.out.println("*************");
//
//        }
//
//            return true;
//    }
//}
