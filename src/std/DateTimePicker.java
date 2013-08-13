package std;

public final class DateTimePicker extends javax.swing.JPanel {
    //<editor-fold defaultstate="collapsed" desc="Control Declarations">
    private int Month = 0;
    private int Year = 0;
    private String Day = null;
    private String SelectedDate = null;
    private javax.swing.JPopupMenu PopUp = null;
    private javax.swing.JButton[] Buttons = null;
    private javax.swing.JPanel DatePanel;
    private javax.swing.JPanel DaysPanel;
    private javax.swing.JLabel DropDownMonthLabel;
    private javax.swing.JLabel MondayLabel;
    private javax.swing.JLabel TuesdayLabel;
    private javax.swing.JLabel WednesdayLabel;
    private javax.swing.JLabel ThursdayLabel;
    private javax.swing.JLabel FridayLabel;
    private javax.swing.JLabel SaturdayLabel;
    private javax.swing.JLabel SundayLabel;
    private javax.swing.JLabel MonthNext;
    private javax.swing.JLabel MonthPrevious;
    private javax.swing.JLabel CurrentDateLabel;
    private javax.swing.JTextField DayLabel;
    private javax.swing.JTextField MonthLabel;
    private javax.swing.JTextField YearLabel;
    private javax.swing.JPanel DatePickerPanel;
    private javax.swing.JToggleButton DateToggleButton;
    private static String WeekDays[] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    // </editor-fold>
    
    public DateTimePicker() {
        Components();        
        SetLayout();
        this.SetDays();
        this.setSize(500, 500);
        this.SetListeners();
        Month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
        Year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);      
        ShowDate();
    }
    
    private void SetDays() {
        for (int I = 0; I < WeekDays.length; ++I) {
            javax.swing.JLabel Label = new javax.swing.JLabel("    " + WeekDays[I]);
            Label.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
            DaysPanel.add(Label);
        }
        
        for (int I = 0; I < Buttons.length; ++I) {
            final int Selection = I;
            Buttons[I] = new javax.swing.JButton();
            //Buttons[I].setFont(new Font("Arial", Font.BOLD, 12));
            Buttons[I].setFocusPainted(false);
            DaysPanel.add(Buttons[I], java.awt.BorderLayout.WEST);
            
            Buttons[I].setBackground(java.awt.Color.white);
            Buttons[I].addActionListener(new java.awt.event.ActionListener() {
                @Override public void actionPerformed(java.awt.event.ActionEvent e) {
                    Day = Buttons[Selection].getActionCommand();
                    PopUp.setVisible(false);
                    DateToggleButton.setSelected(false);

                    for (int I = 0; I < Buttons.length; ++I) {
                        if (Buttons[I].getText() != null) {
                            Buttons[I].setBackground(java.awt.Color.white);
                        }
                    }
                    DayLabel.setText(Day);
                    Buttons[Selection].setBackground(java.awt.Color.getColor(null, 0xB0E0E6));
                }
            });
        }
    }
    
    private void SetListeners() {
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                requestFocusInWindow();
            }
        });
        
        this.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            @Override public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
                if (DayLabel.hasFocus()) {
                    DayChange(e.getWheelRotation() < 0);
                } else if (MonthLabel.hasFocus()) {
                    MonthChange(e.getWheelRotation() < 0);
                } else if (YearLabel.hasFocus()) {
                    YearChange(e.getWheelRotation() < 0);
                }
            }
        });
        
        PopUp.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            @Override public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {
                DateToggleButton.setSelected(false);
            }

            @Override public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
            }

            @Override public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {
            }
        });
        
        DateToggleButton.addItemListener(new java.awt.event.ItemListener() {
            @Override public void itemStateChanged(java.awt.event.ItemEvent e) {
                if(e.getStateChange() == java.awt.event.ItemEvent.SELECTED){
                    PopUp.add(DatePanel);
                    PopUp.show(DatePickerPanel, 0, DatePickerPanel.getHeight());
                }
            }
        });
        
        DayLabel.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusLost(java.awt.event.FocusEvent e) {
                DayLabel.setBackground(java.awt.Color.white);
            }
        });
        
        DayLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                DayLabel.setBackground(java.awt.Color.getColor(null, 0xB0E0E6));
                MonthLabel.setBackground(java.awt.Color.white);
                YearLabel.setBackground(java.awt.Color.white);
            }
        });
        
        DayLabel.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override public void keyPressed(java.awt.event.KeyEvent e) {
                int Key = e.getKeyCode();
                if (Key == java.awt.event.KeyEvent.VK_UP) {
                    DayChange(true);
                } else if (Key == java.awt.event.KeyEvent.VK_DOWN) {
                    DayChange(false);
                }
            }
        });
        
        DayLabel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            @Override public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
                DayChange(e.getWheelRotation() < 0);
            }
        });
        
        MonthLabel.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusLost(java.awt.event.FocusEvent e) {
                MonthLabel.setBackground(java.awt.Color.white);
            }
        });
        
        MonthLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                DayLabel.setBackground(java.awt.Color.white);
                MonthLabel.setBackground(java.awt.Color.getColor(null, 0xB0E0E6));
                YearLabel.setBackground(java.awt.Color.white);
            }
        });
        
        MonthLabel.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override public void keyPressed(java.awt.event.KeyEvent e) {
                int Key = e.getKeyCode();
                if (Key == java.awt.event.KeyEvent.VK_UP) {
                    MonthChange(true);
                } else if (Key == java.awt.event.KeyEvent.VK_DOWN) {
                    MonthChange(false);
                }
            }
        });
        
        MonthLabel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            @Override public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
                MonthChange(e.getWheelRotation() < 0);
            }
        });
        
        YearLabel.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusLost(java.awt.event.FocusEvent e) {
                YearLabel.setBackground(java.awt.Color.white);
            }
        });
        
        YearLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                DayLabel.setBackground(java.awt.Color.white);
                MonthLabel.setBackground(java.awt.Color.white);
                YearLabel.setBackground(java.awt.Color.getColor(null, 0xB0E0E6));
            }
        });
        
        YearLabel.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override public void keyPressed(java.awt.event.KeyEvent e) {
                int Key = e.getKeyCode();
                if (Key == java.awt.event.KeyEvent.VK_UP) {
                    YearChange(true);
                } else if (Key == java.awt.event.KeyEvent.VK_DOWN) {
                    YearChange(false);
                }
            }
        });
        
        YearLabel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            @Override public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
                YearChange(e.getWheelRotation() < 0);
            }
        });
        
        MonthPrevious.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                --Month;
                ShowDate();
            }
        });
        
        MonthNext.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                ++Month;
                ShowDate();
            }
        });
    }
    
    private void DayChange(boolean Forward) {
        java.util.Calendar Calendar = java.util.Calendar.getInstance();
        Calendar.set(Year, Month, 1);
                
        if (Forward) {
            int LastDay = Calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
            int Value = Integer.parseInt(DayLabel.getText());
            if (Value < LastDay) {
                Day = String.valueOf(++Value);
                DayLabel.setText(Day);
            } else {
                Day = "1";
                Calendar.set(Year, ++Month, 1);
                DayLabel.setText(Day);
                MonthLabel.setText(new java.text.SimpleDateFormat("MMMM").format(Calendar.getTime()));
            }
            } else {
                int Value = Integer.parseInt(DayLabel.getText());
                if (Value > 1) {
                    Day = String.valueOf(--Value);
                    DayLabel.setText(Day);
                } else {
                    Calendar.set(Year, --Month, 1);
                    Day = String.valueOf(Calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
                    DayLabel.setText(Day);
                }
        }
        ShowDate();
    }
    
    private void MonthChange(boolean Forward) {
        java.util.Calendar Calendar = java.util.Calendar.getInstance();
        
        if (Forward) {
            Calendar.set(Year, ++Month, 1);
        } else {
            Calendar.set(Year, --Month, 1);
        }
        ShowDate();
    }
    
    private void YearChange(boolean Forward) {
        java.util.Calendar Calendar = java.util.Calendar.getInstance();
        
        if (Forward) {
            Calendar.set(++Year, Month, 1);
        } else {
            Calendar.set(--Year, Month, 1);
        }
        ShowDate();
    }
    
    private void ShowDate() {
        for (int I = 0; I < Buttons.length; ++I) {
            Buttons[I].setText(null);
        }
        
        java.util.Calendar Info = java.util.Calendar.getInstance();
        Info.set(Year, Month, 1);
        java.text.SimpleDateFormat Format = new java.text.SimpleDateFormat("MMMM    yyyy");
        DropDownMonthLabel.setText(Format.format(Info.getTime()));
        
        int DayOfTheWeek = Info.get(java.util.Calendar.DAY_OF_WEEK);
        int DaysInTheMonth = Info.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        
        for (int I = DayOfTheWeek - 1, Days = 1; Days <= DaysInTheMonth; ++I, ++Days) {
            Buttons[I].setBackground(java.awt.Color.white);
            Buttons[I].setText(String.valueOf(Days));
            Buttons[I].setEnabled(true);
        }
        
        for (int I = 0; I < Buttons.length; ++I) {
            if (Buttons[I].getText() == null) {
                Buttons[I].setEnabled(false);
                Buttons[I].setBackground(java.awt.Color.getColor(null, 0xEEEEEE));
            }
        }
        
        MonthLabel.setText(new java.text.SimpleDateFormat("MMMM").format(Info.getTime()));
        YearLabel.setText(new java.text.SimpleDateFormat("yyyy").format(Info.getTime()));
        
        if (Day != null) {
            java.text.SimpleDateFormat SelectedDateFormat = new java.text.SimpleDateFormat("dd-MM-yyyy");
            Info.set(Year, Month, Integer.parseInt(Day));
            SelectedDate = SelectedDateFormat.format(Info.getTime());
        } else {
            java.util.Calendar Calendar = java.util.Calendar.getInstance();
            DayLabel.setText(new java.text.SimpleDateFormat("dd").format(Calendar.getTime()));
            CurrentDateLabel.setText("Today's Date Is:    " + new java.text.SimpleDateFormat("dd-MMMM-yyyy").format(Calendar.getTime()));
        }
    }
    
    public String GetCurrentDate() {
        java.util.Calendar Calendar = java.util.Calendar.getInstance();
        return new java.text.SimpleDateFormat("dd-MM-yyyy").format(Calendar.getTime());
    }
    
    public String GetSelectedDate() {
        if (SelectedDate == null) {
            java.util.Calendar Calendar = java.util.Calendar.getInstance();
            java.text.SimpleDateFormat SelectedDateFormat = new java.text.SimpleDateFormat("dd-MM-yyyy");
            SelectedDate = SelectedDateFormat.format(Calendar.getTime());
        }     
        
        ShowDate();
        
        return SelectedDate;
    }
    
    private void Components() {
        Buttons = new javax.swing.JButton[42];
        PopUp = new javax.swing.JPopupMenu();
        DatePanel = new javax.swing.JPanel();
        DaysPanel = new javax.swing.JPanel();
        DropDownMonthLabel = new javax.swing.JLabel();
        MonthNext = new javax.swing.JLabel();
        MonthPrevious = new javax.swing.JLabel();
        SundayLabel = new javax.swing.JLabel();
        SaturdayLabel = new javax.swing.JLabel();
        FridayLabel = new javax.swing.JLabel();
        ThursdayLabel = new javax.swing.JLabel();
        WednesdayLabel = new javax.swing.JLabel();
        TuesdayLabel = new javax.swing.JLabel();
        MondayLabel = new javax.swing.JLabel();
        DatePickerPanel = new javax.swing.JPanel();
        DayLabel = new javax.swing.JTextField();
        MonthLabel = new javax.swing.JTextField();
        YearLabel = new javax.swing.JTextField();
        DateToggleButton = new javax.swing.JToggleButton();
        CurrentDateLabel = new javax.swing.JLabel("Today's Date is: ");
        
        PopUp.setPopupSize(298, 255); //298, 237
        DatePanel.setBackground(new java.awt.Color(0xB0E0E6));
        DaysPanel.setBackground(new java.awt.Color(255, 255, 255));
        DatePickerPanel.setBackground(new java.awt.Color(255, 255, 255));
        DropDownMonthLabel.setText("Month");
        MonthNext.setText(">");
        MonthPrevious.setText("<");
        MondayLabel.setText("Mon");
        TuesdayLabel.setText("Tue");
        WednesdayLabel.setText("Wed");
        ThursdayLabel.setText("Thu");
        FridayLabel.setText("Fri");
        SaturdayLabel.setText("Sat");
        SundayLabel.setText("Sun");
        DayLabel.setText("Day");
        MonthLabel.setText("Month");
        YearLabel.setText("Year");
        DateToggleButton.setText("...");
        
        java.awt.Font Style = new java.awt.Font("Arial", java.awt.Font.BOLD, 12);
        MonthPrevious.setFont(Style);
        MonthNext.setFont(Style);
        DropDownMonthLabel.setFont(Style);
        CurrentDateLabel.setFont(Style);
        
        DayLabel.setEditable(false);
        MonthLabel.setEditable(false);
        YearLabel.setEditable(false);
        
        DayLabel.setBorder(null);
        MonthLabel.setBorder(null);
        YearLabel.setBorder(null);
        
        DayLabel.setForeground(java.awt.Color.black);
        DayLabel.setBackground(java.awt.Color.white);
        MonthLabel.setForeground(java.awt.Color.black);
        MonthLabel.setBackground(java.awt.Color.white);
        YearLabel.setForeground(java.awt.Color.black);
        YearLabel.setBackground(java.awt.Color.white);
        
        DayLabel.setFont(javax.swing.UIManager.getFont("Label.font"));
        MonthLabel.setFont(javax.swing.UIManager.getFont("Label.font"));
        YearLabel.setFont(javax.swing.UIManager.getFont("Label.font"));
        MonthLabel.setAlignmentX(CENTER_ALIGNMENT);
    }
    
    private void SetLayout() {
        DaysPanel.setBackground(new java.awt.Color(255, 255, 255));
        DaysPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        java.awt.GridLayout DaysPanelLayout = new java.awt.GridLayout(7, 7);
        DaysPanel.setPreferredSize(new java.awt.Dimension(290, 167));
        DaysPanel.setLayout(DaysPanelLayout);

        javax.swing.GroupLayout DatePanelLayout = new javax.swing.GroupLayout(DatePanel);
        DatePanel.setLayout(DatePanelLayout);
        DatePanelLayout.setHorizontalGroup(
            DatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DatePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DatePanelLayout.createSequentialGroup()
                        .addComponent(MonthPrevious)
                        .addGap(95, 95, 95)
                        .addComponent(DropDownMonthLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(MonthNext))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DatePanelLayout.createSequentialGroup()
                            .addComponent(CurrentDateLabel)
                            .addGap(45, 45, 45)))
                .addContainerGap())
            .addComponent(DaysPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        DatePanelLayout.setVerticalGroup(
            DatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DatePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DropDownMonthLabel)
                    .addGap(25, 25, 25)
                    .addComponent(MonthNext)
                    .addComponent(MonthPrevious))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DaysPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addComponent(CurrentDateLabel))
        );

        DatePickerPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        
        javax.swing.GroupLayout DatePickerPanelLayout = new javax.swing.GroupLayout(DatePickerPanel);
        DatePickerPanel.setLayout(DatePickerPanelLayout);
        DatePickerPanel.setPreferredSize(new java.awt.Dimension(210, 20));
        DatePickerPanelLayout.setHorizontalGroup(
            DatePickerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DatePickerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DayLabel)
                .addGap(18, 18, 18)
                .addComponent(MonthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(YearLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(DateToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        DatePickerPanelLayout.setVerticalGroup(
            DatePickerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DatePickerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(DayLabel)
                .addComponent(MonthLabel)
                .addComponent(YearLabel)
                .addComponent(DateToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(DatePickerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(DatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(DatePickerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(DatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(132, Short.MAX_VALUE))
        );
    }
}
