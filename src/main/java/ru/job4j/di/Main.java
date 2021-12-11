package ru.job4j.di;

public class Main {
    public static void main(String[] args) {
        Context context = new Context();
        context.reg(ConsoleInput.class);
        context.reg(Store.class);
        context.reg(StartUI.class);

        StartUI ui = context.get(StartUI.class);
        ConsoleInput cI = context.get(ConsoleInput.class);

        ui.add("Petr Arsentev");
        ui.add(cI.input());
        ui.print();
    }
}