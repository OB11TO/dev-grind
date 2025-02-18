package ru.ob11to.architecture.srp.report;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import ru.ob11to.architecture.srp.adapter.CalendarAdapterXml;
import ru.ob11to.architecture.srp.formatter.ReportDateTimeParser;
import ru.ob11to.architecture.srp.model.Employee;
import ru.ob11to.architecture.srp.model.EmployeeWrapper;
import ru.ob11to.architecture.srp.store.Store;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;

public class ReportEngineXml implements Report {

    private final Store store;

    public ReportEngineXml(Store store) {
        this.store = store;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        List<Employee> employees = store.findBy(filter);
        XmlAdapter<String, Calendar> parser = new CalendarAdapterXml(new ReportDateTimeParser());
        try {
            JAXBContext context = JAXBContext.newInstance(EmployeeWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setAdapter(parser);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            EmployeeWrapper wrapper = new EmployeeWrapper();
            wrapper.setEmployees(employees);
            StringWriter writer = new StringWriter();
            marshaller.marshal(wrapper, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}