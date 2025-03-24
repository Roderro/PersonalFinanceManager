package my.finance.ioconsole.main.viewReport.reportforperiod;

import my.finance.utils.DatePeriodHelper;
import my.finance.security.AppSession;
import my.finance.transport.ConsoleOutput;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

@Component
@Lazy
@Profile("console")
public class ReportForPeriod extends AbstractReportForPeriodPanel {
    static final String TEXT = "Отчет за определенный период";

    public ReportForPeriod() {
        super();
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
            output.printf("Введите начальную дату и время в формате %s:", ConsoleOutput.DATE_PATTERN);
            String startInput = input.nextLine();
            startDate = LocalDate.parse(startInput, ConsoleOutput.dateFormat);
            output.printf("Введите конечную дату и время в формате %s:", ConsoleOutput.DATE_PATTERN);
            String endInput = input.nextLine();

            endDate = LocalDate.parse(endInput, ConsoleOutput.dateFormat);
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
