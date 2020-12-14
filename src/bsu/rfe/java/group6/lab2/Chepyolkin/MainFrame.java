package bsu.rfe.java.group6.lab2.Chepyolkin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.*;
import java.util.OptionalDouble;

public class MainFrame extends JFrame {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 480;
    private JLabel labelX = new JLabel("X:");
    private JLabel labelY = new JLabel("Y:");
    private JLabel labelZ = new JLabel("Z:");
    private JLabel labelResult = new JLabel("Результат:");
    private JTextField fieldX = new JTextField("0", 6);
    private JTextField fieldY = new JTextField("0", 6);
    private JTextField fieldZ = new JTextField("0", 6);
    private JTextField fieldResult = new JTextField("0", 18);
    private JButton buttonMC = new JButton("MC");
    private JButton buttonMPlus = new JButton("M+");
    private JButton buttonClear = new JButton("Очистить поля");
    private JButton buttonCalc = new JButton("Вычислить");
    private JRadioButton radio1 = new JRadioButton("Формула 1");
    private JRadioButton radio2 = new JRadioButton("Формула 2");
    private ButtonGroup radioButtons = new ButtonGroup();
    private Box hboxFormulaType = Box.createHorizontalBox();
    private int formulaID = 1;
    private Double result = 0.0;
    private Double sum = 0.0;

    public MainFrame() {

        super("Calculator");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        this.setBounds((toolkit.getScreenSize().width - WIDTH) / 2, (toolkit.getScreenSize().height - HEIGHT) / 2, WIDTH, HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        hboxFormulaType.add(Box.createHorizontalGlue());
        addRadioButton("Формула 1", 1);
        addRadioButton("Формула 2", 2);
        radioButtons.setSelected(radioButtons.getElements().nextElement().getModel(), true);
        hboxFormulaType.add(Box.createHorizontalGlue());
        hboxFormulaType.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Контейнер ввода переменных
        fieldX.setMaximumSize(fieldX.getPreferredSize());
        fieldY.setMaximumSize(fieldY.getPreferredSize());
        fieldZ.setMaximumSize(fieldZ.getPreferredSize());
        fieldResult.setMaximumSize(fieldResult.getPreferredSize());
        Box hboxVariables = Box.createHorizontalBox();
        hboxVariables.add(Box.createHorizontalGlue());
        hboxVariables.add(labelX);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(fieldX);
        hboxVariables.add(Box.createHorizontalStrut(100));
        hboxVariables.add(labelY);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(fieldY);
        hboxVariables.add(Box.createHorizontalStrut(100));
        hboxVariables.add(labelZ);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(fieldZ);
        hboxVariables.add(Box.createHorizontalGlue());
        //hboxVariables.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Кнопка МС
        buttonMC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sum = 0.0;
            }
        });

        // Кнопка M+
        buttonMPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sum += result;
                fieldResult.setText(sum.toString());
            }
        });

        // Контейнер вывода вычислений
        Box hboxResult = Box.createHorizontalBox();
        hboxResult.add(Box.createHorizontalGlue());
        hboxResult.add(labelResult);
        hboxResult.add(Box.createHorizontalStrut(10));
        hboxResult.add(fieldResult);
        hboxResult.add(Box.createHorizontalStrut(10));
        hboxResult.add(buttonMC);
        hboxResult.add(Box.createHorizontalStrut(10));
        hboxResult.add(buttonMPlus);
        hboxResult.add(Box.createHorizontalGlue());
        hboxResult.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Кнопка "Вычислить"
        buttonCalc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Double x = Double.valueOf(fieldX.getText());
                    Double y = Double.valueOf(fieldY.getText());
                    Double z = Double.valueOf(fieldZ.getText());
                    if (formulaID == 1) {
                        result = calculate1(x, y, z);
                    }
                    else {
                        result = calculate2(x, y, z);
                    }
                    fieldResult.setText(result.toString());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Ошибка в формате записи " +
                            "числа с плавающей точкой", "Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

         // Кнопка "Очистить поля"
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fieldX.setText("0");
                fieldY.setText("0");
                fieldZ.setText("0");
                fieldResult.setText("0");
            }
        });

        // Контейнер с кнопками "Вычислить", "Очистить поля"
        Box hboxActionButtons = Box.createHorizontalBox();
        hboxActionButtons.add(Box.createHorizontalGlue());
        hboxActionButtons.add(buttonCalc);
        hboxActionButtons.add(Box.createHorizontalStrut(30));
        hboxActionButtons.add(buttonClear);
        hboxActionButtons.add(Box.createHorizontalGlue());
        hboxResult.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Контейнер контейнеров
        Box contentBox = Box.createVerticalBox();
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(hboxFormulaType);
        contentBox.add(hboxVariables);
        contentBox.add(hboxResult);
        contentBox.add(hboxActionButtons);
        contentBox.add(Box.createVerticalGlue());
        getContentPane().add(contentBox, BorderLayout.CENTER);
    }

    // Формула 1
    public double calculate1(double x, double y, double z) {
        return Math.sin(Math.sin(y) + Math.exp(Math.cos(y)) + z * z) * Math.pow(Math.sin(3.1415 * y * y) + Math.log(x * x), 1 / 4);
    }

    // Формула 2
    public double calculate2(double x, double y, double z) {
        return Math.atan(Math.pow(z, 1 / x)) / (y * y + z * Math.sin(Math.log(x)));
    }

    // Метод создания и добавления в группу радио-кнопок
    private void addRadioButton(String buttonName, int formulaID) {

        JRadioButton button = new JRadioButton(buttonName);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.formulaID = formulaID;
            }
        });

        radioButtons.add(button);
        hboxFormulaType.add(button);
    }


}
