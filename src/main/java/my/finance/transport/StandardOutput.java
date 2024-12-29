package my.finance.transport;

import my.finance.models.AppTransaction;
import my.finance.models.BudgetCategory;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class StandardOutput implements Output {
    private final PrintStream output;
    private final PrintStream error;
    public static final String DATE_PATTERN = "dd-MM-yyyy";
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public StandardOutput() {
        output = System.out;
        error = System.err;
    }

    @Override
    public void print(String str) {
        output.print(str);
        output.flush();
    }

    public void println() {
        output.println();
        output.flush();
    }


    @Override
    public void println(String str) {
        output.println(str);
        output.flush();
    }

    @Override
    public void error(String str) {
        error.println(str);
        error.flush();
    }

    @Override
    public void printf(String format, Object... args) {
        output.printf(format, args);
        output.flush();
    }

    public void printLine(int size){
        String lineString = "-".repeat(size);
        output.println(lineString);
    }

    public void fPrintCategories(List<BudgetCategory> incomeCategories,
                                 List<BudgetCategory> expenseCategories) {

        if (incomeCategories.isEmpty()) {
            output.println("Категорий дохода пока что нет, но их можно добавить!");
        } else {
            output.println("Категории дохода:");
            output.println(ListCategoriesTofString(incomeCategories, 0, true));
        }

        if (expenseCategories.isEmpty()) {
            output.println("Категорий расхода пока что нет, но их можно добавить!");
        } else {
            output.println("Категории расхода:");
            output.println(ListCategoriesTofString(expenseCategories, incomeCategories.size(), false));
        }
    }

    public String ListCategoriesTofString(List<BudgetCategory> userCategories, int offset, boolean isIncome) {
        String resultString;
        if (isIncome) {
            resultString = userCategories.stream()
                    .map(category -> String.format("%d. %s", userCategories.indexOf(category) + 1 + offset,
                            category.getCategoryName()))
                    .collect(Collectors.joining("\n"));
        } else {
            resultString = userCategories.stream()
                    .map(category -> String.format("%d. %s(лимит:%.2f)", userCategories.indexOf(category) + 1 + offset,
                            category.getCategoryName(), category.getBudgetLimit()))
                    .collect(Collectors.joining("\n"));
        }
        return resultString;
    }

    public void fPrintAppTransaction(List<AppTransaction> appTransactions) {
        if (appTransactions.isEmpty()) {
            output.println("Транзакций пока что нет, но их можно добавить!");
        } else {
            output.println("Ваши транзакции:");
            output.printf("%-4s%-15s%-15s%-15s %n", "№", "Категория", "Сумма операции", "Описания");
            int i = 0;
            for (AppTransaction appTransaction : appTransactions) {
                i++;
                output.printf("%-4s%-15s%-15.2f%-30s %n", i, appTransaction.getBudgetCategory().getCategoryName(),
                        appTransaction.getAmount(), appTransaction.getDescription());
            }
        }
    }

    public void fPrintReportByPeriod(double income, double expenses, List<AppTransaction> appTransactions, LocalDate startDate, LocalDate endDate) {
        output.printf("Отчет за период: c %s до %s %n", startDate.format(dateFormat), endDate.format(dateFormat));
        output.printf("""
                Общий доход: %.2f
                Общий расходы: %.2f
                """, income, expenses);
        fPrintAppTransaction(appTransactions);
    }
}
