package ru.ob11to.architecture.srp.report;

import org.junit.jupiter.api.Test;
import ru.ob11to.architecture.solid.srp.model.Employee;
import ru.ob11to.architecture.solid.srp.report.Report;
import ru.ob11to.architecture.solid.srp.report.ReportEngineJson;
import ru.ob11to.architecture.solid.srp.store.MemoryStore;
import ru.ob11to.architecture.solid.srp.store.Store;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.*;

class ReportEngineJsonTest {

    @Test
    void getReportJson() {
        Report engine = getReport();
        String ex = """
                [
                  {
                    "name": "John Doe",
                    "hired": "08:06:2023 17:41",
                    "fired": "08:06:2023 17:41",
                    "salary": 5000.0
                  },
                  {
                    "name": "Jane Smith",
                    "hired": "08:06:2023 17:41",
                    "fired": "08:06:2023 17:41",
                    "salary": 6000.0
                  }
                ]""";
        assertThat(engine.generate(em -> true)).isEqualTo(ex);
    }

    private static Report getReport() {
        Store store = new MemoryStore();
        Employee employee = new Employee("John Doe",
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                5000.0);
        Employee employee1 = new Employee("Jane Smith",
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                6000.0);
        store.add(employee);
        store.add(employee1);
        return new ReportEngineJson(store);
    }
}