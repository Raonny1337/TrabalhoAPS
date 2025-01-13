package model.loan;

import java.time.LocalDate;

public class Proof {
    private int id;
    private int userId;
    private String bookTitle;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
                "Aqui está seu comprovante de pedido de empréstimo:\n\n" +
                        "============================================\n" +
                        "ID do Empréstimo    : %d\n" +
                        "Usuário ID          : %d\n" +
                        "Título do Livro     : '%s'\n" +
                        "Data de Empréstimo  : %s\n" +
                        "Data de Devolução   : %s\n" +
                        "============================================",
                id, userId, bookTitle, loanDate, returnDate);
    }
}
