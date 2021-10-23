import java.io.*;
import java.sql.*;
import java.util.Locale;
import java.util.Scanner;
import javax.xml.transform.Result;

public class DictionaryManagement {

    Dictionary dic = new Dictionary();

    public Word addWord(String S1, String S2) {
        Word word = new Word();
        word.setWord_explain(S2);
        word.setWord_target(S1);
        return word;
    }

    /**
     * Hàm dùng để thêm từ mới vào Arraylist và Database
     */

    public void DictionaryInsert() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập từ tiếng Anh: ");
        String targ = scanner.nextLine();
        targ.toLowerCase(Locale.ROOT);
        //word.setWord_target(targ);
        System.out.print("Nhập giải nghĩa: ");
        String expl = scanner.nextLine();
        // word.setWord_explain(expl);
        boolean checkDuplicate = checkDuplicate(targ);
        dic.diction.add(new Word(targ, expl));
        if(checkDuplicate)                  //đoạn này toi k nhớ là để if(checkDuplicate) hay (check == true) nữa
        {
            update(targ,expl);
        }
        else {
            InsertIntoDatabase(targ, expl);
        }
    }

    public void insertfromCommandLine() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập số lượng từ vựng: ");
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            //Word word = new Word();
            DictionaryInsert();
        }
    }

    /**
     * Xét xem có bị trùng từ add vào không. nêu có thì update từ đấy thay vì insert từ mới
     * @param target từ cần xét
     */

    public boolean checkDuplicate(String target) {
        int i=0;
        while(i<dic.diction.size())
        {
            if(dic.diction.get(i).getWord_target().equals(target))
            {
                return true;
            }
            i++;
        }
        return false;
    }

    public void insertFromFile() {
        BufferedReader br = null;
        File file = new File("dictionaries.txt");
        try {
            br = new BufferedReader(new FileReader(file));

            //declare string variable

            String st;
            while ((st = br.readLine()) != null) {
                String[] SplitString;
                SplitString = st.split("\t");
                //addWord(SplitString[0], SplitString[1]);
                dic.diction.add(addWord(SplitString[0], SplitString[1]));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Dùng để tìm kiếm từ (phải nhập đúng)
     */

    public void dictionaryLookup() {
        Scanner scanner = new Scanner(System.in);
        boolean sosanh = false;
        System.out.print("Nhập từ cần tìm: ");
        String S1 = scanner.nextLine();
        S1.toLowerCase(Locale.ROOT);
        for (int i = 0; i < dic.diction.size(); i++) {
            if (S1.equals(dic.diction.get(i).getWord_target())) {
                System.out.print("Giải nghĩa: ");
                System.out.println(dic.diction.get(i).getWord_explain());
                sosanh = true;
            }
        }

        if (!sosanh) {
            System.out.println("Không tìm thấy từ");
        }

    }

    /**
     * Dùng để xóa từ khỏi Array và Database
     */

    public void dictionaryDelete(){          //String S1
        boolean sosanh = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập từ cần xóa: ");
        String S1 = scanner.nextLine();
        S1.toLowerCase(Locale.ROOT);
        for (int i = 0; i < dic.diction.size(); i++) {
            if (S1.equals(dic.diction.get(i).getWord_target())) {
                sosanh = true;
                dic.diction.remove(i);
                RemovefromDatabase(S1);

            }
        }
        if (!sosanh) {
            System.out.println("Không tìm thấy từ");
        }
    }


    /**
     * Nhận dữ liệu từ điển từ database
     */

    public void GetWordFromDatabase() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:./sqlite/dict_hh.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT word,description FROM av");
            while (resultSet.next()) {
                // System.out.println(resultSet.getString("word" )+" \t "+resultSet.getString("description"));
                dic.diction.add(new Word(resultSet.getString("word"), resultSet.getString("description")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dictionaryExportToFile() throws IOException {
        File file = new File("./dictionaries.txt");
        if (file.exists()) {
            FileWriter writer = new FileWriter("dictionaries.txt");
        }
    }

    /**
     * Tạo Connection để sử dụng nhanh
     */

    private Connection connect() {
        String url = "jdbc:sqlite:./sqlite/dict_hh.db";
        // String url = "jdbc:sqlite:/C:\\sythanh\\sqlite\\test\\Newdtb.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Chỉnh sửa database
     * @param target từ tiếng anh
     * @param explain nghĩa tiếng việt
     */

    public void update(String target, String explain) {
        String sql = "UPDATE av SET description = ? "
                + "WHERE word = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, explain);
            pstm.setString(2, target);
            pstm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * Hàm chỉnh sửa từ
     */

    public void EditWord() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Chon tu tieng anh: ");
        String targ = scanner.nextLine();
        System.out.println("chon tu tieng viet: ");
        String expl = scanner.nextLine();
        update(targ, expl);
    }

    /**
     * Chèn từ mới vào database
     * @param target từ tiếng anh
     * @param explain nghĩa tiếng việt
     */

    public void InsertIntoDatabase(String target, String explain) {
        String sql = "INSERT INTO av(word,description) VALUES(?,?)";
        try {
            Connection connection = this.connect();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, target);
            pstm.setString(2, explain);
            pstm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xóa từ mới khỏi database
     * @param target từ cần xoá
     */

    public void RemovefromDatabase(String target) {
        String sql = "DELETE FROM av WHERE word = ?";
        try {
            Connection connection = this.connect();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, target);
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * xuất ra arraylist ra file dictionaries
     */

    public void ExportToFile() {
        try {
            File file = new File("dictionaries.txt");
            FileOutputStream f = new FileOutputStream(file);
            OutputStreamWriter fw = new OutputStreamWriter(f);
            for (Word i: dic.diction) {
                String line = i.getWord_target() + "\t" + i.getWord_explain();
                fw.write(line);
                fw.write(System.lineSeparator()) ;
            }
            fw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
