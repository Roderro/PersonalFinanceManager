package my.finance.transport;

import my.finance.models.AppTransaction;
import my.finance.models.BudgetCategory;

import java.time.LocalDate;
import java.util.List;

public interface Output {

    void print(String str);

    void println(String str);

    void error(String str);

    void printf(String format, Object... args);

    void printLine(int size);

    void fPrintCategories(List<BudgetCategory> incomeCategories,
                          List<BudgetCategory> expenseCategories);

    void fPrintListCategories(List<BudgetCategory> userCategories, int offset, boolean isIncome);

    void fPrintAppTransaction(List<AppTransaction> appTransactions);

    void fPrintReportByPeriod(double income, double expenses, List<AppTransaction> appTransactions, LocalDate startDate, LocalDate endDate);

}
