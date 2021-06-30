import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.swing.*;
import javax.swing.border.*;
//======================================================Don't modify below===============================================================//
enum PieceType {king, queen, bishop, knight, rook, pawn, none}
enum PlayerColor {black, white, none}

public class ChessBoard {
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JPanel chessBoard;
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private Piece[][] chessBoardStatus = new Piece[8][8];
    private ImageIcon[] pieceImage_b = new ImageIcon[7];
    private ImageIcon[] pieceImage_w = new ImageIcon[7];
    private JLabel message = new JLabel("Enter Reset to Start");

    ChessBoard(){
        initPieceImages();
        initBoardStatus();
        initializeGui();
    }

    public final void initBoardStatus(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++) chessBoardStatus[j][i] = new Piece();
        }
    }

    public final void initPieceImages(){
        pieceImage_b[0] = new ImageIcon(new ImageIcon("./img/king_b.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
        pieceImage_b[1] = new ImageIcon(new ImageIcon("./img/queen_b.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
        pieceImage_b[2] = new ImageIcon(new ImageIcon("./img/bishop_b.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
        pieceImage_b[3] = new ImageIcon(new ImageIcon("./img/knight_b.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
        pieceImage_b[4] = new ImageIcon(new ImageIcon("./img/rook_b.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
        pieceImage_b[5] = new ImageIcon(new ImageIcon("./img/pawn_b.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
        pieceImage_b[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));

        pieceImage_w[0] = new ImageIcon(new ImageIcon("./img/king_w.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
        pieceImage_w[1] = new ImageIcon(new ImageIcon("./img/queen_w.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
        pieceImage_w[2] = new ImageIcon(new ImageIcon("./img/bishop_w.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
        pieceImage_w[3] = new ImageIcon(new ImageIcon("./img/knight_w.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
        pieceImage_w[4] = new ImageIcon(new ImageIcon("./img/rook_w.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
        pieceImage_w[5] = new ImageIcon(new ImageIcon("./img/pawn_w.png").getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
        pieceImage_w[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
    }

    public ImageIcon getImageIcon(Piece piece){
        if(piece.color.equals(PlayerColor.black)){
            if(piece.type.equals(PieceType.king)) return pieceImage_b[0];
            else if(piece.type.equals(PieceType.queen)) return pieceImage_b[1];
            else if(piece.type.equals(PieceType.bishop)) return pieceImage_b[2];
            else if(piece.type.equals(PieceType.knight)) return pieceImage_b[3];
            else if(piece.type.equals(PieceType.rook)) return pieceImage_b[4];
            else if(piece.type.equals(PieceType.pawn)) return pieceImage_b[5];
            else return pieceImage_b[6];
        }
        else if(piece.color.equals(PlayerColor.white)){
            if(piece.type.equals(PieceType.king)) return pieceImage_w[0];
            else if(piece.type.equals(PieceType.queen)) return pieceImage_w[1];
            else if(piece.type.equals(PieceType.bishop)) return pieceImage_w[2];
            else if(piece.type.equals(PieceType.knight)) return pieceImage_w[3];
            else if(piece.type.equals(PieceType.rook)) return pieceImage_w[4];
            else if(piece.type.equals(PieceType.pawn)) return pieceImage_w[5];
            else return pieceImage_w[6];
        }
        else return pieceImage_w[6];
    }

    public final void initializeGui(){
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        JButton startButton = new JButton("Reset");
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                initiateBoard();
            }
        });

        tools.add(startButton);
        tools.addSeparator();
        tools.add(message);

        chessBoard = new JPanel(new GridLayout(0, 8));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);
        ImageIcon defaultIcon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
        Insets buttonMargin = new Insets(0,0,0,0);
        for(int i=0; i<chessBoardSquares.length; i++) {
            for (int j = 0; j < chessBoardSquares[i].length; j++) {
                JButton b = new JButton();
                b.addActionListener(new ButtonListener(i, j));
                b.setMargin(buttonMargin);
                b.setIcon(defaultIcon);
                if((j % 2 == 1 && i % 2 == 1)|| (j % 2 == 0 && i % 2 == 0)) b.setBackground(Color.WHITE);
                else b.setBackground(Color.gray);
                b.setOpaque(true);
                b.setBorderPainted(false);
                chessBoardSquares[j][i] = b;
            }
        }

        for (int i=0; i < 8; i++) {
            for (int j=0; j < 8; j++) chessBoard.add(chessBoardSquares[j][i]);

        }
    }

    public final JComponent getGui() {
        return gui;
    }

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ChessBoard cb = new ChessBoard();
                JFrame f = new JFrame("Chess");
                f.add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);
                f.setResizable(false);
                f.pack();
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }

    //================================Utilize these functions========================================//

    class Piece{
        PlayerColor color;
        PieceType type;

        Piece(){
            color = PlayerColor.none;
            type = PieceType.none;
        }
        Piece(PlayerColor color, PieceType type){
            this.color = color;
            this.type = type;
        }
    }

    public void setIcon(int x, int y, Piece piece){
        chessBoardSquares[y][x].setIcon(getImageIcon(piece));
        chessBoardStatus[y][x] = piece;
    }

    public Piece getIcon(int x, int y){
        return chessBoardStatus[y][x];
    }

    public void markPosition(int x, int y){
        chessBoardSquares[y][x].setBackground(Color.pink);
    }

    public void unmarkPosition(int x, int y){
        if((y % 2 == 1 && x % 2 == 1)|| (y % 2 == 0 && x % 2 == 0)) chessBoardSquares[y][x].setBackground(Color.WHITE);
        else chessBoardSquares[y][x].setBackground(Color.gray);
    }

    public void setStatus(String inpt){
        message.setText(inpt);
    }

    public void initiateBoard(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++) setIcon(i, j, new Piece());
        }
        setIcon(0, 0, new Piece(PlayerColor.black, PieceType.rook));
        setIcon(0, 1, new Piece(PlayerColor.black, PieceType.knight));
        setIcon(0, 2, new Piece(PlayerColor.black, PieceType.bishop));
        setIcon(0, 3, new Piece(PlayerColor.black, PieceType.queen));
        setIcon(0, 4, new Piece(PlayerColor.black, PieceType.king));
        setIcon(0, 5, new Piece(PlayerColor.black, PieceType.bishop));
        setIcon(0, 6, new Piece(PlayerColor.black, PieceType.knight));
        setIcon(0, 7, new Piece(PlayerColor.black, PieceType.rook));
        for(int i=0;i<8;i++){
            setIcon(1, i, new Piece(PlayerColor.black, PieceType.pawn));
            setIcon(6, i, new Piece(PlayerColor.white, PieceType.pawn));
        }
        setIcon(7, 0, new Piece(PlayerColor.white, PieceType.rook));
        setIcon(7, 1, new Piece(PlayerColor.white, PieceType.knight));
        setIcon(7, 2, new Piece(PlayerColor.white, PieceType.bishop));
        setIcon(7, 3, new Piece(PlayerColor.white, PieceType.queen));
        setIcon(7, 4, new Piece(PlayerColor.white, PieceType.king));
        setIcon(7, 5, new Piece(PlayerColor.white, PieceType.bishop));
        setIcon(7, 6, new Piece(PlayerColor.white, PieceType.knight));
        setIcon(7, 7, new Piece(PlayerColor.white, PieceType.rook));
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++) unmarkPosition(i, j);
        }
        onInitiateBoard();
    }
//======================================================Don't modify above==============================================================//


    //======================================================Implement below=================================================================//
    /*
    reset 누르면 initiate

    members:
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JPanel chessBoard;
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private Piece[][] chessBoardStatus = new Piece[8][8];
    private ImageIcon[] pieceImage_b = new ImageIcon[7];
    private ImageIcon[] pieceImage_w = new ImageIcon[7];
    private JLabel message = new JLabel("Enter Reset to Start");


    public final void initBoardStatus()  :  chessBoardStatus를 new Piece(none, none)으로 초기화
    public final void initPieceImages()  :  pieceImage array들에 image를 load
    public ImageIcon getImageIcon(Piece piece)  :   piece에 해당하는 image를 받아옴
    public final void initializeGui()  :  gui initialize

    class Piece  :  member: PlayerColor color, PieceType type

    public void setIcon(int x, int y, Piece piece)  :  chessBoardSquares[y][x](JButton)에 piece에 맞는 image 넣어줌 & chessBoardStatus에 piece 저장
    public Piece getIcon(int x, int y)  :  [y][x]에 있는 piece return
    public void markPosition(int x, int y)  :  [y][x]의 체스판 색깔을 pink로 변경
    public void unmarkPosition(int x, int y)  : [y][x]의 체스판 색깔을 원래대로 변경(흰 or 회)
    public void setStatus(String inpt)  :  message의 text를 변경

    public void initiateBoard()  :  체스판의 원래 위치에 모든 말들을 놓고 체스판 색깔 설정


    Icon: JButton (/w ImageIcon) & Piece

    chessboard size: 8 x 8

    enum PieceType {king, queen, bishop, knight, rook, pawn, none}

     */

    private class Location {
        int x;
        int y;
        public Location(int x_, int y_) {
            x = x_;
            y = y_;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Location location = (Location) o;
            return x == location.x && y == location.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private class PieceAndLocation {
        Piece piece;
        Location location;
        public PieceAndLocation(Piece p, Location lc) {
            piece = p;
            location = lc;
        }
    }

    enum MagicType {MARK, CHECK, CHECKMATE};
    // enum Turn {BLACK, WHITE};
    enum SelectType {POSITION, PIECE};
    private int selX, selY;
    private boolean check, checkmate, end = false;

    final static String blackTurn = "BLACK's TURN";
    final static String whiteTurn = "WHITE's TURN";
    String prevText;

    PlayerColor turn = PlayerColor.black;
    SelectType select = SelectType.PIECE;

    private String getTurnMessage(PlayerColor turn) {
        if (turn.equals(PlayerColor.black)) {
            return blackTurn;
        } else {
            return whiteTurn;
        }
    }

    private void showScope(Piece piece, int x, int y) {
        // x, y에 위치한 piece의 이동 가능한 경로를 보여줌
        Location[] locations = getMovableLocations(piece, x, y);
        for (Location location : locations) {
            markPosition(location.x, location.y);
        }
    }

    private void blindScope() {
        // 보여줬던 scope 없애줌
/*        Location[] locations = getMovableLocations(piece, x, y);
        for (Location location : locations) {
            unmarkPosition(location.x, location.y);
        }*/


        // else
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j){
                unmarkPosition(i, j);
            }
        }

    }

    private void movePiece(Piece piece, int fromX, int fromY, int toX, int toY) {
        // piece를 x에서 y로 move
        setIcon(toX, toY, piece);
        removePiece(fromX, fromY);
    }

    private void removePiece(int x, int y) {
        // x, y에 있는 piece를 지움
        setIcon(x, y, new Piece());
    }

    private boolean existPiece(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return false;
        }
        return getIcon(x, y).type != PieceType.none;
    }

    private boolean isCheck(PlayerColor turn) {
        // turn 색의 king이 체크됐는지 확인
        ArrayList<Location> checkLocations = new ArrayList<>();
        Location kingLocation = new Location(0, 0);
        for (int i = 0;  i < 8; ++i) {
            for (int j = 0 ; j < 8; ++j) {
                Piece piece = getIcon(i, j);
                if (!turn.equals(piece.color)) {
                    Location[] locations = getMovableLocations(piece, i, j);
                    checkLocations.addAll(Arrays.asList(locations));
                }
                if (turn.equals(piece.color) && piece.type.equals(PieceType.king)) {
                    kingLocation.x = i;
                    kingLocation.y = j;
                }
            }
        }

        for (Location location : checkLocations) {
            if (location.equals(kingLocation)) {
/*                if (turn.equals(PlayerColor.black)) {
                    message.setText(blackTurn + " / CHECK");
                } else {
                    message.setText(whiteTurn + " / CHECK");
                }*/
                return true;
            }
        }

        return false;
    }

    private boolean isCheckMate(PlayerColor turn) {
        if (!isCheck(turn)) {
            return false;
        }

        List<PieceAndLocation> pieceList = new LinkedList<>();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (getIcon(i, j).color.equals(turn)) {
                    pieceList.add(new PieceAndLocation(getIcon(i, j), new Location(i, j)));
                }
            }
        }

        for (PieceAndLocation pieceAndLocation : pieceList) {
            // 가능한 move 전부 해봄
            Piece pieceToMove = pieceAndLocation.piece;
            int fromX = pieceAndLocation.location.x;
            int fromY = pieceAndLocation.location.y;
            Location[] locations = getMovableLocations(pieceToMove, fromX, fromY);
            for (Location location : locations) {
                Piece prevPiece = getIcon(location.x, location.y);
                movePiece(pieceToMove, fromX, fromY, location.x, location.y);
                if (!isCheck(turn)) {
                    // check 해소 가능

                    // move back
                    movePiece(pieceToMove, location.x, location.y, fromX, fromY);
                    setIcon(location.x, location.y, prevPiece);

                    return false;
                }

                // move back
                movePiece(pieceToMove, location.x, location.y, fromX, fromY);
                setIcon(location.x, location.y, prevPiece);
            }
        }

        return true;
    }

    private boolean canMove(Piece piece, int fromX, int fromY, int toX, int toY) {
        // fromX, fromY에서 toX, toY로 갈 수 있는지 확인
        if (fromX < 0 || fromY < 0) {
            // error
            return false;
        }
        if (getIcon(fromX, fromY).type != piece.type) {
            // error
            return false;
        }

        Location[] locations = getMovableLocations(piece, fromX, fromY);
        for (Location location : locations) {
            if (location.x == toX && location.y == toY) {
                return true;
            }
        }

        return false;
    }

    private ArrayList<Location> getVerticalLocations(Piece piece, int x, int y) {
        ArrayList<Location> returnList = new ArrayList<>();
        for (int currX = x+1; currX < 8; ++currX) {
            // 아래방향
            if (existPiece(currX, y)) {
                if (piece.color.equals(getIcon(currX, y).color)) {
                    // 같은 색의 말
                    break;
                } else {
                    // 다른 색의 말
                    Location lc = new Location(currX, y);
                    returnList.add(lc);
                    break;
                }
            }
            if (currX >= 0) {
                Location lc = new Location(currX, y);
                returnList.add(lc);
            }
        }

        for (int currX = x-1; currX >= 0; --currX) {
            // 위방향
            if (existPiece(currX, y)) {
                if (piece.color.equals(getIcon(currX, y).color)) {
                    // 같은 색의 말
                    break;
                } else {
                    // 다른 색의 말
                    Location lc = new Location(currX, y);
                    returnList.add(lc);
                    break;
                }
            }
            if (currX <= 7) {
                Location lc = new Location(currX, y);
                returnList.add(lc);
            }
        }
        return returnList;
    }

    private ArrayList<Location> getHorizontalLocations(Piece piece, int x, int y) {
        ArrayList<Location> returnList = new ArrayList<>();
        for (int currY = y+1; currY < 8; ++currY) {
            // 오른쪽
             if (existPiece(x, currY)) {
                if (piece.color.equals(getIcon(x, currY).color)) {
                    // 같은 색의 말
                    break;
                } else {
                    // 다른 색의 말
                    Location lc = new Location(x, currY);
                    returnList.add(lc);
                    break;
                }
            }
            if (currY >= 0) {
                Location lc = new Location(x, currY);
                returnList.add(lc);
            }
        }

        for (int currY = y-1; currY >= 0; --currY) {
            // 왼쪽
            if (existPiece(x, currY)) {
                if (piece.color.equals(getIcon(x, currY).color)) {
                    // 같은 색의 말
                    break;
                } else {
                    // 다른 색의 말
                    Location lc = new Location(x, currY);
                    returnList.add(lc);
                    break;
                }
            }
            if (currY <= 7) {
                Location lc = new Location(x, currY);
                returnList.add(lc);
            }
        }
        return returnList;
    }

    private ArrayList<Location> getDiagonalLocations(Piece piece, int x, int y) {
        ArrayList<Location> returnList = new ArrayList<>();
        /*
        +1, +1부터 +1, +1씩 : 오른 아래
        +1, -1부터 +1, -1씩: 왼 아래
        -1, +1부터 -1, +1씩: 오른 위
        -1, -1부터 -1, -1씩: 왼 위
         */

        int[] dxArr = {1, 1, -1, -1};
        int[] dyArr = {1, -1, 1, -1};

        for (int i = 0; i < 4; ++i) {
            int currX = x + dxArr[i];
            int currY = y + dyArr[i];
            while ((0 <= currX && currX < 8) && (0 <= currY && currY < 8)) {
                if (existPiece(currX, currY)) {
                    if (!piece.color.equals(getIcon(currX, currY).color)) {
                        // 다른 색의 말
                        Location lc = new Location(currX, currY);
                        returnList.add(lc);
                    }
                    break;
                }
                Location lc = new Location(currX, currY);
                returnList.add(lc);
                currX += dxArr[i];
                currY += dyArr[i];
            }

        }
        return returnList;
    }

    private Location[] getMovableLocations(Piece piece, int x, int y) {
        ArrayList<Location> returnList = new ArrayList<>();
        if(piece.type.equals(PieceType.king)) {
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    int currX = x + i;
                    int currY = y + j;

                    if (existPiece(currX, currY)) {
                        // 같은 색: 그냥 pass, 다른 색: 넣어줌
                        if (piece.color.equals(getIcon(currX, currY).color)) {
                            continue;
                        }
                    }

                    if ((0 <= currX && currX <= 7) && (0 <= currY && currY <= 7)) {
                        if (currX != x || currY != y) {
                            Location lc = new Location(currX, currY);
                            returnList.add(lc);
                        }
                    }
                }
            }
        } else if(piece.type.equals(PieceType.queen)) {
            returnList.addAll(getHorizontalLocations(piece, x, y));
            returnList.addAll(getVerticalLocations(piece, x, y));
            returnList.addAll(getDiagonalLocations(piece, x, y));
        } else if(piece.type.equals(PieceType.bishop)) {
            returnList.addAll(getDiagonalLocations(piece, x, y));
        } else if(piece.type.equals(PieceType.knight)) {
            int[] dx = {-1, 1, -1, 1, -2, 2, -2, 2};
            int[] dy = {-2, -2, 2, 2, -1, -1, 1, 1};
            for (int i = 0; i < 8; ++i) {
                if ((0 <= x + dx[i] && x + dx[i] < 8) && (0 <= y + dy[i] && y + dy[i] < 8)) {
                    if (!piece.color.equals(getIcon(x + dx[i], y + dy[i]).color)) {
                        returnList.add(new Location(x + dx[i], y + dy[i]));
                    }
                }
            }
        } else if(piece.type.equals(PieceType.rook)) {
            returnList.addAll(getHorizontalLocations(piece, x, y));
            returnList.addAll(getVerticalLocations(piece, x, y));
        } else if(piece.type.equals(PieceType.pawn)) {
            // 시작할때: 2칸 else: 상대방향으로 1칸
            if (piece.color.equals(PlayerColor.black)) {
                // 검정
                if (x == 1) {
                    // 시작
                    for (int currX = x+1; currX < x+3; ++currX) {
                        // 아래방향
                        if (existPiece(currX, y)) {
                            break;
                        }
                        if (currX >= 0) {
                            Location lc = new Location(currX, y);
                            returnList.add(lc);
                        }
                    }
                } else {
                    // 시작 x
                    if (x+1 < 7 && x+1 >= 0) {
                        if (!existPiece(x+1, y)) {
                            Location lc = new Location(x+1, y);
                            returnList.add(lc);
                        }
                    }
                }

                // 대각 공격
                if (existPiece(x+1, y+1) && !piece.color.equals(getIcon(x+1, y+1).color)) {
                    Location lc = new Location(x+1, y+1);
                    returnList.add(lc);
                }
                if (existPiece(x+1, y-1) && !piece.color.equals(getIcon(x+1, y-1).color)) {
                    Location lc = new Location(x+1, y-1);
                    returnList.add(lc);
                }
            } else if (piece.color.equals(PlayerColor.white)) {
                // 흰
                if (x == 6) {
                    // 시작
                    for (int currX = x-1; currX >= x-2; --currX) {
                        // 위방향
                        if (existPiece(currX, y)) {
                            break;
                        }
                        if (currX <= 7) {
                            Location lc = new Location(currX, y);
                            returnList.add(lc);
                        }
                    }
                } else {
                    // 시작 x
                    if (x-1 < 7 && x-1 >= 0) {
                        if (!existPiece(x-1, y)) {
                            Location lc = new Location(x-1, y);
                            returnList.add(lc);
                        }
                    }
                }

                // 대각 공격
                if (existPiece(x-1, y+1) && !piece.color.equals(getIcon(x-1, y+1).color)) {
                    Location lc = new Location(x-1, y+1);
                    returnList.add(lc);
                }
                if (existPiece(x-1, y-1) && !piece.color.equals(getIcon(x-1, y-1).color)) {
                    Location lc = new Location(x-1, y-1);
                    returnList.add(lc);
                }
            }
        }
        return returnList.toArray(new Location[0]);
    }

    private void inverseTurn() {
        if (turn.equals(PlayerColor.black)) {
            turn = PlayerColor.white;
        } else {
            turn = PlayerColor.black;
        }
        message.setText(getTurnMessage(turn));
    }


    class ButtonListener implements ActionListener{
        int x;
        int y;
        ButtonListener(int x, int y){
            this.x = x;
            this.y = y;
        }
        public void actionPerformed(ActionEvent e) {	// Only modify here
            // (x, y) is where the click event occurred
            if (!end) {
                if (select == SelectType.PIECE) {
                    // message.setText(getTurnMessage(turn) + "/ CHOOSE POSITION");
                    // piece가 있는 위치를 클릭해야 의미 있음
                    if (existPiece(x, y) && turn.equals(getIcon(x, y).color)) {
                        Piece pieceToMove = getIcon(x, y);
                        showScope(pieceToMove, x, y);
                        prevText = message.getText();
                        message.setText(getTurnMessage(turn) + "/ CHOOSE POSITION");
                        select = SelectType.POSITION;
                        selX = x;
                        selY = y;
                    }
                } else if (select == SelectType.POSITION) {
                    // message.setText(getTurnMessage(turn) + "/ CHOOSE PIECE");
                    Piece pieceToMove = getIcon(selX, selY);
                    Piece pieceToRemove = getIcon(x, y); // none이거나 실제 말이거나

                    // 갈수 있는 위치여야함.
                    if (canMove(pieceToMove, selX, selY, x, y)) {
                        movePiece(pieceToMove, selX, selY, x, y);
                        if (isCheck(turn)) {
                            // 움직였는데 내가 check가 돼버림 or check가 해소되지 않음 : 원래대로
                            movePiece(pieceToMove, x, y, selX, selY);
                            setIcon(x, y, pieceToRemove);
                            message.setText(getTurnMessage(turn)+ "/ CANNOT MOVE: CHECKED!");
                        } else {
                            // move 성공적
                            inverseTurn();
                            if (isCheck(turn)) {
                                // 상대방이 check됨
                                message.setText(getTurnMessage(turn) + "/ CHECK");
                            }
                            if (isCheckMate(turn)) {
                                // checkmate
                                end = true;
                                message.setText(getTurnMessage(turn) + "/ CHECKMATE");
                            }
                            if (pieceToRemove.type.equals(PieceType.king) && pieceToRemove.color.equals(turn)) {
                                // king dead
                                end = true;
                                message.setText(getTurnMessage(turn) + "/ KING IS DEAD");
                            }
                        }
                        //indicateCheck(turn);
                    } else {
                        if (isCheck(turn)) {
                            message.setText(getTurnMessage(turn) + "/ CHECK");
                        } else {
                            message.setText(getTurnMessage(turn));
                        }
                    }
                    // move여부에 상관없는 공통 작업
                    select = SelectType.PIECE;
                    selX = -1;
                    selY = -1;
                    blindScope();
                }
            }
        }
    }

    void onInitiateBoard(){
        // black turn으로 message 초기화
        turn = PlayerColor.black;
        setStatus("Reset / " + blackTurn);
        end = false;
    }
}
