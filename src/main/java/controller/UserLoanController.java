package controller;

import java.util.ArrayList;
import java.util.List;

import core.utils.ChangeNotifier;
import model.book.Book;
import model.loan.Loan;
import service.LoanService;

public class UserLoanController extends ChangeNotifier {

    private final LoanService loanService;

    private List<Book> availableBooks = new ArrayList<Book>();
    private Loan createdLoan = null;

    public UserLoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    public void showLoans(int userId) {
        try {
            System.out.println("");
            System.out.println("==== Livros disponíveis ===");
            List<Loan> loans = loanService.getAllLoansByUserId(userId);
            if (loans.size() <= 0) {
                System.out.println("Nenhum empréstimo encontrado");
                return;
            }
            for (Loan loan : loans) {
                System.out.println("ID: " + loan.getId() + ", Título do livro: " + loan.getBookTitle() + ", Pedido: "
                        + loan.getLoanDate() + ", Data de Retorno: " + loan.getReturnDate() + ", Status: "
                        + loan.getStatus());
            }
        } catch (Throwable e) {
            System.out.println("Ocorreu um erro ao buscar os empréstimos");
            System.out.println("");
            return;
        }
        System.out.println("");
    }

    public Loan getCreatedLoan() {
        return createdLoan;
    }

    public void loanBook(String isbn, int userId) {
        isLoading = true;
        notifyObservers();
        try {

            createdLoan = loanService.loanBook(isbn, userId);

        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
            hasError = true;
        } catch (Throwable e) {
            hasError = true;
            errorMessage = e.getMessage();
        } finally {
            isLoading = false;
            notifyObservers();
        }
    }

    public void getBooks() {
        isLoading = true;
        notifyObservers();
        try {
            availableBooks = loanService.getAvailableBooksToLoan();
        } catch (Throwable e) {
            hasError = true;
            errorMessage = e.getMessage();
        } finally {
            isLoading = false;

            notifyObservers();
        }
    }

    public List<Book> getAvailableBooks() {
        return availableBooks;
    }

    @Override
    public void dispose() {
        availableBooks.clear();
        super.dispose();
    }
}
