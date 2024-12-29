package my.finance.ioconsole.main.viewReport.reportforperiod;

import my.finance.utils.DatePeriodHelper;
import my.finance.security.AppSession;
import my.finance.transport.StandardOutput;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

public class ReportForPeriod extends AbstractReportForPeriodPanel {
    static final String TEXT = "Отчет за определенный период";

    public ReportForPeriod(AppSession appSession) {
        super(appSession);
    }

    @Override
    public void action() {
        try {
            getCustomPeriod();
            super.action();
        } catch (RuntimeException e) {
            output.println(e.getMessage());
            waitEnter();
        }
    }


    private void getCustomPeriod() {
        LocalDate startDate = null;
        LocalDate endDate = null;
        try {
            output.printf("Введите начальную дату и время в формате %s:", StandardOutput.DATE_PATTERN);
            String startInput = input.nextLine();
            startDate = LocalDate.parse(startInput, StandardOutput.dateFormat);
            output.printf("Введите конечную дату и время в формате %s:", StandardOutput.DATE_PATTERN);
            String endInput = input.nextLine();

            endDate = LocalDate.parse(endInput, StandardOutput.dateFormat);
            if (startDate.isAfter(endDate)) {
                throw new InputMismatchException();
            }
            period = DatePeriodHelper.getCustomPeriod(startDate, endDate);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Неверный формат даты!");
        } catch (InputMismatchException e) {
            throw new RuntimeException("Начальная дата должна быть раньше конечной");
        }
    }
}
