package com.example.biblioteca.core.utils;

import java.util.ArrayList;
import java.util.List;

public class ChangeNotifier {
    public boolean hasError = false;
    public String errorMessage = null;
    public boolean isLoading = false;

    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        List<Observer> observersCopy = new ArrayList<Observer>(this.observers);
        for (Observer observer : observersCopy) {
            observer.update();
        }
    }

    public void dispose() {
        observers.clear();
        hasError = false;
        errorMessage = null;
        isLoading = false;
    }
}
