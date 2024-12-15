package newGame.logic;

import java.util.*;
import newGame.tcp.*;

public class GameRoom {
    private String code; // Unique game room code
    private ClientHandler professor;
    private List<ClientHandler> students = new ArrayList<>();
    private List<Map<String, Object>> questions; // Questions for the game
    private char[][] board = new char[3][3]; // Tic Tac Toe board
    private int currentTurn = 0; // Current player's turn (student index)

    public GameRoom(String code, ClientHandler professor, List<Map<String, Object>> questions) {
        this.code = code;
        this.professor = professor;
        this.questions = questions;
        initializeBoard();
    }

    public String getCode() {
        return code;
    }

    public ClientHandler getProfessor() {
        return professor;
    }

    public List<ClientHandler> getStudents() {
        return students;
    }

    public void addStudent(ClientHandler student) {
        students.add(student);
    }

    public List<Map<String, Object>> getQuestions() {
        return questions;
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public boolean makeMove(int row, int col, char symbol) {
        if (board[row][col] == ' ') {
            board[row][col] = symbol;
            return true;
        }
        return false;
    }

    public void displayBoard() {
        for (int i = 0; i < 3; i++) {
            System.out.println(Arrays.toString(board[i]));
        }
    }

    public void setQuestions(List<Map<String, Object>> questions) {
        this.questions = questions;
    }
}
