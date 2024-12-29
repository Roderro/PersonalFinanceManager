package my.finance.ioconsole;

import my.finance.security.AppSession;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public abstract class AbstractMainPanel extends AbstractPanel {
    protected List<Class<?>> children = new ArrayList<>();

    public AbstractMainPanel(AppSession appSession) {
        super(appSession);
    }

    public List<Class<?>> getChildren() {
        return children;
    }

    @Override
    public final void printPanel() {
        try {
            populateChildren();
            printChildren();
            printSpecialPanel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public final void action() {
        output.print("Введите число: ");
        try {
            int inputNumber = input.nextInt();
            if (inputNumber > 0) {
                if (inputNumber <= children.size())
                    nextPanelClass = (Class<Panel>) children.get(inputNumber - 1);
                else {
                    nextPanelClass = getSpecialPanel(inputNumber);
                }
            } else throw new InputMismatchException();
        } catch (InputMismatchException e) {
            System.out.println("Введено не правильное число!");
        }
    }


    protected void populateChildren() throws IOException, ClassNotFoundException {
        Class<?> currentClass = this.getClass();
        String packageName = currentClass.getPackage().getName();
        List<String> subPackages = getSubPackages(packageName);
        children.addAll(getClassesInCurrentPackage(packageName, false));
        children.remove(currentClass);
        if (!subPackages.isEmpty()) {
            for (String subPackage : subPackages) {
                children.addAll(getClassesInCurrentPackage(subPackage, true));
            }
        }
    }


    private void printChildren() throws NoSuchFieldException, IllegalAccessException {
        for (int i = 0; i < children.size(); i++) {
            Class<?> child = (Class<?>) children.get(i);
            Field fieldText = child.getDeclaredField("TEXT");
            fieldText.setAccessible(true);
            Object text = fieldText.get(null);
            output.printf("%2d: %s \n", i + 1, text);
        }
    }

    protected void printSpecialPanel() {
        output.printf("%2d: %s \n", children.size() + 1, "Назад");
        output.printf("%2d: %s \n", children.size() + 2, "Закрыть приложение");
    }


    protected Class<? extends Panel> getSpecialPanel(int inputNumber) {
        if (inputNumber == children.size() + 1) {
            return parentClass;
        } else if (inputNumber == children.size() + 2) {
            return CloseApplicationPanel.class;
        } else throw new InputMismatchException();
    }


    // Метод для получения списка подпакетов на 1 уровень ниже
    protected List<String> getSubPackages(String packageName) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<String> subPackages = new ArrayList<>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File directory = new File(resource.getFile());
            File[] files = directory.listFiles(File::isDirectory);
            if (files != null) {
                for (File file : files) {
                    if (file.getName().startsWith("_")) {
                        continue;
                    }
                    subPackages.add(packageName + "." + file.getName());
                }
            }
        }
        return subPackages;
    }

}
