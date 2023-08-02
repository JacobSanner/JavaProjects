import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class MemoryGame extends JFrame {
    int score = 0;
    JPanel mainContents = new JPanel();
    JPanel buttonPanel = new JPanel();
    final String scoreText = "Welcome new player. Your current score is: ";
    JLabel mainLabel = new JLabel(scoreText + score);
    final int rows = 3;
    final int column = 4;
    final int totalRounds = (rows * column) / 2;
    List<JButton> buttonList = new ArrayList<JButton>();
    List<Color> colorList = new ArrayList<Color>();
    JButton lastClickedButton = null;
    int totalMatched = 0;

    public MemoryGame() {
        super("Memory Game");

        add(mainContents);
        mainContents.setLayout(new BorderLayout());
        mainContents.add(mainLabel, BorderLayout.NORTH);
        mainContents.add(buttonPanel, BorderLayout.CENTER);

        mainContents.setVisible(true);

        GridLayout glout = new GridLayout(rows, column);
        buttonPanel.setLayout(glout);

        setCrossPlatformLook();

        for (int i = 0; i < rows * column; i++) {
            JButton btn = new JButton();
            btn.addActionListener(e -> ButtonClicked(e));
            buttonList.add(btn);
        }

        for (JButton b : buttonList) {
            buttonPanel.add(b);
        }

        InitColors();

        setSize(300, 400);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void InitColors() {
        colorList.add(Color.BLACK);
        colorList.add(Color.BLACK);
        colorList.add(Color.RED);
        colorList.add(Color.RED);
        colorList.add(Color.MAGENTA);
        colorList.add(Color.MAGENTA);
        colorList.add(Color.CYAN);
        colorList.add(Color.CYAN);
        colorList.add(Color.YELLOW);
        colorList.add(Color.YELLOW);
        colorList.add(Color.PINK);
        colorList.add(Color.PINK);

        // this allows us to randomize colors by swapping the values of two different indexes
        for (int i = 0; i < colorList.size(); i++) {
            int newPosition = (int)(Math.random() * colorList.size());
            Color newPositionColor = colorList.get(newPosition);
            Color currentPositionColor = colorList.get(i);
            colorList.set(i, newPositionColor);
            colorList.set(newPosition, currentPositionColor);
        }
    }

    public void ButtonClicked(ActionEvent e) {
        Object btnObj = e.getSource();
        int index = buttonList.indexOf(btnObj);
        JButton currentBtn = ((JButton) (btnObj));
        currentBtn.setBackground(colorList.get(index));

        System.out.println("last clicked button" + lastClickedButton);

        if (lastClickedButton == null) { // if first button click
            lastClickedButton = currentBtn;
        } else { // if second button click
            boolean isMatch = IsColorMatch(lastClickedButton, currentBtn);

            if (isMatch) {
                currentBtn.setEnabled(false);
                lastClickedButton.setEnabled(false);
                score += 10;
                totalMatched++;

                mainLabel.setText(scoreText + score);

                if (totalMatched == totalRounds) { 
                    JOptionPane.showMessageDialog(this, "You've won! The total score is  " + score);
                }
            } else {
                int tempScore = score - 1;
                score = tempScore < 0 ? 0 : tempScore;
                JOptionPane.showMessageDialog(this, "The two colors did not match " + score);
                mainLabel.setText(scoreText + score);

                currentBtn.setEnabled(true);
                lastClickedButton.setEnabled(true);
                currentBtn.setBackground(null);
                lastClickedButton.setBackground(null);
            }

            lastClickedButton = null;
        }
    }
    
    private boolean IsColorMatch(JButton lastClickedButton, JButton currentButton) {
        int index1 = buttonList.indexOf(lastClickedButton);
        int index2 = buttonList.indexOf(currentButton);
        return colorList.get(index1).equals(colorList.get(index2));
    }
    
    // add this for the mac users
    public void setCrossPlatformLook() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
