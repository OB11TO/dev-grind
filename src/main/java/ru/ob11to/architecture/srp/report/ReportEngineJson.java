package ru.ob11to.architecture.srp.report;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.ob11to.architecture.srp.adapter.CalendarAdapter;
import ru.ob11to.architecture.srp.model.Employee;
import ru.ob11to.architecture.srp.store.Store;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.function.Predicate;

public class ReportEngineJson implements Report {

    private final Store store;
    private final Gson gson;

    public ReportEngineJson(Store store) {
        this.store = store;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(GregorianCalendar.class, new CalendarAdapter()) // преобразование календаря
                .setPrettyPrinting()  // форматирование в json
                .create();
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        List<Employee> list = store.findBy(filter);
        return gson.toJson(list);
    }
}
