package org.example;

import java.sql.Date;


public record Transactions(Date date, User fromUser, User toUser, Account fromAcc, Account toAcc,
                           double amount) {}
