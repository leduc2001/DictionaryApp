package group11.dictapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

public class DictionaryManagement {

    private Dictionary dic = new Dictionary();

    public ArrayList<Word> load(){
        return dic.diction;
    }

    /**
     * Thêm từ mới vào Database
     */

    public void dictionaryInsert(String targ, String expl) {
        targ = targ.toLowerCase(Locale.ROOT);
        InsertIntoDatabase(targ, expl);
        dic.diction.add(new Word(targ, expl));
    }

    /**
     * Kiểm tra từ được add có bị trùng không.
     * @param target từ cần xét
     */

    public int checkDuplicate(String target) {
        int i=0;
        while(i<dic.diction.size())
        {
            if(dic.diction.get(i).getWord_target().equals(target))
            {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Dùng để tìm kiếm từ (phải nhập đúng)
     */

    public String dictionaryLookup(String S1) {
        S1 = S1.toLowerCase(Locale.ROOT);
        for (int i = 0; i < dic.diction.size(); i++) {
            if (S1.equals(dic.diction.get(i).getWord_target())) {
                return dic.diction.get(i).getWord_explain();
            }
        }
        return null;
    }

    /**
     * Xoá từ khỏi arraylist và database.
     * @param S1 từ cần xoá
     */

    public void dictionaryDelete(String S1) {
        S1 = S1.toLowerCase(Locale.ROOT);
        for (int i = 0; i < dic.diction.size(); i++) {
            if (S1.equals(dic.diction.get(i).getWord_target())) {
                dic.diction.remove(i);
                RemovefromDatabase(S1);
                break;
            }
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
                dic.diction.add(new Word(resultSet.getString("word"), resultSet.getString("description")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tạo Connection để sử dụng nhanh
     */

    private Connection connect() {
        String url = "jdbc:sqlite:./sqlite/dict_hh.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Chỉnh sửa từ trong database
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
        int index = checkDuplicate(target);
        dic.diction.get(index).setWord_explain(explain);
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
}
