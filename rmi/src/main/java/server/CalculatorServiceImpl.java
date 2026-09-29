package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorServiceImpl
        extends UnicastRemoteObject
        implements CalculatorService {

    private final HistoryService history;

    public CalculatorServiceImpl(HistoryService history)
            throws RemoteException {

        super();
        this.history = history;
    }

    @Override
    public double add(double a, double b) throws RemoteException {

        double result = a + b;

        history.record("ADD(" + a + ", " + b + ") = " + result);

        return result;
    }

    @Override
    public double sub(double a, double b) throws RemoteException {

        double result = a - b;

        history.record("SUB(" + a + ", " + b + ") = " + result);

        return result;
    }

    @Override
    public double mul(double a, double b) throws RemoteException {

        double result = a * b;

        history.record("MUL(" + a + ", " + b + ") = " + result);

        return result;
    }

    @Override
    public double div(double a, double b) throws RemoteException {

        if (b == 0) {
            throw new IllegalArgumentException("Division par zéro");
        }

        double result = a / b;

        history.record("DIV(" + a + ", " + b + ") = " + result);

        return result;
    }
}