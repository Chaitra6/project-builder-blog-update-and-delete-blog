package dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Blog;
import utility.ConnectionManager;

public class BlogDaoImpl implements BlogDaoInterface {

	@Override
	public void insertBlog(Blog blog) throws Exception {
		int blogid = blog.getBlogId();
		String blog_title = blog.getBlogTitle();
		String blogDesc = blog.getBlogDescription();
		java.util.Date postedOn = blog.getPostedOn();

		ConnectionManager cm = new ConnectionManager();

		// insert all details into DB
		String sql = "insert into BLOG(BLOG_ID, BLOG_TITLE, BLOG_DESCRIPTION, BLOG_POSTED)VALUES(?,?,?,?)";

		// CREATE STATEMENT OBJECT
		PreparedStatement st = cm.getConnection().prepareStatement(sql);

		st.setInt(1, blogid);
		st.setString(2, blog_title);
		st.setString(3, blogDesc);
		st.setDate(4, (Date) postedOn);

		int execute = st.executeUpdate();
		if (execute > 0) {
			System.out.println("Executed");
		} else {
			System.out.println("NotExeccuted");
		}
		cm.getConnection().close();

	}

	@Override
	public Blog selectBlog(int blogid) throws Exception {
		Blog blog = new Blog();
		ConnectionManager con = new ConnectionManager();
		String query = "SELECT * FROM BLOG WHERE BLOG_ID = ?";

		try {
			PreparedStatement st = con.getConnection().prepareStatement(query);

			st.setLong(1, blogid);

			ResultSet rs = st.executeQuery(query);

			blog.setBlogId(rs.getInt("BLOG_ID"));
			blog.setBlogTitle(rs.getString("BLOG_TITLE"));
			blog.setBlogDescription(rs.getString("BLOG_DESCRIPTION"));
			blog.setPostedOn(rs.getDate("BLOG_POSTED"));

			return blog;

		} finally {
			con.getConnection().close();
		}

	}

	@Override
	public List<Blog> selectAllBlogs() throws Exception {
		List<Blog> list = new ArrayList<Blog>();
		ConnectionManager con = new ConnectionManager();

		try {
			
			Statement st = con.getConnection().createStatement();
			Blog blog = new Blog();

			ResultSet rs = st.executeQuery("SELECT * FROM BLOG");

			while (rs.next()) {

				blog.setBlogId(rs.getInt("BLOG_ID"));
				blog.setBlogTitle(rs.getString("BLOG_TITLE"));
				blog.setBlogDescription(rs.getString("BLOG_DESCRIPTION"));
				blog.setPostedOn(rs.getDate("BLOG_POSTED"));

				list.add(blog);
			}
		} finally {
			con.getConnection().close();
		}
		
		return list;
	}

	@Override
	public boolean deleteBlog(int id) throws Exception {
		ConnectionManager con = new ConnectionManager();
		String sql = "DELETE FROM BLOG WHERE BLOG_ID = ?";

		PreparedStatement st = con.getConnection().prepareStatement(sql);
		st.setLong(1, id);

		int rowsDeleted = st.executeUpdate();
		if (rowsDeleted > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean updateBlog(Blog blog) throws SQLException, Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

		ConnectionManager con = new ConnectionManager();
		String sql = "UPDATE BLOG SET BLOG_TITLE=?, BLOG_DESCRIPTION=?, BLOG_POSTED=? WHERE BLOG_ID=?";

		PreparedStatement st = con.getConnection().prepareStatement(sql);
		int blogid = Integer.parseInt(br.readLine());
		String blog_title = br.readLine();
		String blog_desc = br.readLine();
		Date date = (Date) formatter.parse(br.readLine());

		st.setString(1, blog_title);
		st.setString(1, blog_desc);
		st.setDate(3, date);
		st.setLong(4, blogid);

		int rowsDeleted = st.executeUpdate();
		if (rowsDeleted > 0) {
			return true;
		} else {
			return false;
		}
	}

}
