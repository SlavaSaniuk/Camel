package by.bsac.processors;

import by.bsac.annotations.CamelGetter;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CamelGetterAnnotationProcessor extends AbstractProcessor {

    private Messager messager;

    @Override
    public Set<String> getSupportedAnnotationTypes() {

        Set<String> supported_annotations = new LinkedHashSet<>();

        supported_annotations.add(CamelGetter.class.getCanonicalName());

        return supported_annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        //Get all elements annotated with "CamelGetter" annotation
        for (Element element : roundEnv.getElementsAnnotatedWith(CamelGetter.class)) {

            //Check whether annotation user for class
            if (element.getKind() != ElementKind.CLASS) {
                this.messager.printMessage(Diagnostic.Kind.ERROR, "Annotation can be applied only to class.");
                return false;
            }


            //Generate methods
            //Cast to Type element
            TypeElement elem = (TypeElement) element;
            List<VariableElement> variables = new ArrayList<>();
            for (Element e : elem.getEnclosedElements()) {
                if (e.getKind() == ElementKind.FIELD) {
                    variables.add((VariableElement) e);
                }
            }

            for (VariableElement e : variables) {
                System.out.println(e.getSimpleName());
                this.messager.printMessage(Diagnostic.Kind.NOTE, "Class variable: " +e.getSimpleName());
            }

        }

        return true;
    }
}
