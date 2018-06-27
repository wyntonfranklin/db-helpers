
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shady
 */
public class TestClass extends DBModel {
    
    ResultSet tc;
    public String [] columns = {"book_name","description"};
    String [] colTypes = {"int","string","string"};
    public int bookId;
    public String bookName;
    public String bookDescription;
    
    
    public TestClass(){
        super();
    }
    
    public TestClass(int id, String name, String desc){
        bookId = id;
        bookName = name;
        bookDescription = desc;
    }
    
    public TestClass( ResultSet column ){
        setAttributes(column);
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }
    
    

    @Override
    public String getTableName() {
        return "books";
    }
    
    @Override
    public String getPrimaryKey(){
        return "id";
    }
    
    @Override
    public String[] getColumns(){
        return columns;
    }
    
    @Override
    public int currentPk() {
        return bookId;
    }
    
    
    @Override
    public List<TestClass> findAll(){
        List<TestClass> allresults = new ArrayList<>();
        TestClass tstClass = new TestClass();
        tc = getAllColumns();
        try{
            while(tc.next()){
                System.out.println(tc.getString("book_name"));
                tstClass.setAttributes(tc);
                allresults.add(tstClass);
             }   
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
       return allresults;
    }
    
    
    @Override
    public List<?> findAllBySql( String query ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void findByPk(int key){
       tc = getColumn(key);
         try{
            while(tc.next()){
                System.out.println(tc.getString("book_name"));
                setAttributes(tc);
             }   
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void findBySql( String query ){
        tc = getData(findBySqlQuery(query));
            try{
            while(tc.next()){
                setAttributes(tc);
                System.out.println(tc.getString("book_name"));
             }   
        }catch(SQLException e){
             System.out.println(e.getMessage());   
        }
           
    }
    
    @Override
    public void save() {
        try {
            PreparedStatement dbModel = getSaveStatement();
            dbModel.setString(1, bookName);
            dbModel.setString(2, bookDescription);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());   
        }; 
    }
    
    @Override
    public void insert() {
        try {
            PreparedStatement ps = getInsertStatement();
            ps.setString(1, bookName);
            ps.setString(2, bookDescription);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(TestClass.class.getName()).log(Level.SEVERE, null, ex);
        }; 
    }
    
      @Override
    public void update() {
        try {
            PreparedStatement ps = getInsertStatement();
            ps.setString(1, bookName);
            ps.setString(2, bookDescription);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(TestClass.class.getName()).log(Level.SEVERE, null, ex);
        }; 
    }

    
    @Override
    public void setAttributes(ResultSet set) {
        try{
            setBookId(set.getInt("id"));
            setBookName(set.getString("book_name"));
            setBookDescription(set.getString("description"));
        }catch(SQLException e){
            
        }
    }
    
    
    
    public void createSqlString(){
        String [] columns = {"id","book_name","description"};  
        String sqlString = "INSERT INTO pages (";
        sqlString += addDelimiter(columns, ", ") + ") values(";
        sqlString += addQuestionMarks(columns.length, ",");
        
        sqlString += ")";
        System.out.println(sqlString);
    }
    
    public String addDelimiter( String[] text, String delimiter ){
        String output="";
        for(int i=0; i<= text.length-1; i++){
            if(i < text.length-1){
                output += text[i] + delimiter;        
            }else{
                output += text[i];   
            }         
        }
        return output;
    }
    
    public String addQuestionMarks( int count, String delimiter ){
        String output ="";
        String mark = "?";
        for(int i=0; i<= count-1; i++){
            if( i < count-1 ){
                output += mark + delimiter;        
            }else{
                output += mark;   
            }       
        }
        return output;
    }
    
    public static void main(String[] args) {
        TestClass cs = new TestClass();
        cs.findBySql("id=3");
        cs.setBookName("Wynton Book");
        System.out.println(cs.updateQuery());
        cs.save();
        //cs.setBookName("My Favorite BOok");
       // cs.setBookDescription("Welcome home");
       // cs.save();
        //List<TestClass> ls = cs.findAll();
        //cs.findBySql("id=5");
        //cs.createSqlString();
        //System.out.println(cs.getBookName());
        //cs.insert();
        
    }




    
}
