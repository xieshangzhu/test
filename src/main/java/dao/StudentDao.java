package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import bean.Student;

public class StudentDao {

	/**
	 * @Fields jdbcTemplate : TODO
	 */

	private JdbcTemplate jdbcTemplate;

	/**
	 * spring�ṩ����
	 *
	 * @param jdbcTemplate
	 *      ����ֵ���ͣ� void
	 * @author janinus
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * ��ѯ����ѧ��
	 *
	 * @return ����ֵ���ͣ� List<Student>
	 * @author janinus
	 */
	public List<Student> queryAll() {
		String sql = "select id,name,javaScore,htmlScore,cssScore from student";
		//����ѯ���ӳ�䵽Student���У���ӵ�list�У�������
		return jdbcTemplate.query(sql, new StudentMapper());
	}

	/**
	 * ͨ��������ѯ
	 *
	 * @param name
	 * @return ����ֵ���ͣ� List<Student>
	 * @author janinus
	 */
	public List<Student> queryByName(String name) {
		String sql = "select id,name,javaScore,htmlScore,cssScore from student where name like '%" + name + "%'";
		return jdbcTemplate.query(sql, new StudentMapper());
	}

	/**
	 * ���ѧ��
	 *
	 * @param student
	 * @return ����ֵ���ͣ� boolean
	 * @author janinus
	 */
	public boolean addStu(Student student) {
		String sql = "insert into student(id,name,javaScore,htmlScore,cssScore) values(0,?,?,?,?)";
		return jdbcTemplate.update(sql,
				new Object[] { student.getName(), student.getJavaScore(), student.getHtmlScore(), student.getCssScore() },
				new int[] { Types.VARCHAR, Types.DOUBLE, Types.DOUBLE, Types.DOUBLE }) == 1;
	}

	/**
	 * ɾ��ѧ��
	 *
	 * @param id
	 * @return ����ֵ���ͣ� boolean
	 * @author janinus
	 */
	public boolean deleteStu(Integer id) {
		String sql = "delete from student where id = ?";
		return jdbcTemplate.update(sql, id) == 1;
	}

	/**
	 * ����ѧ����Ϣ
	 *
	 * @param student
	 * @return ����ֵ���ͣ� boolean
	 * @author janinus
	 */
	public boolean updateStu(Student student) {
		String sql = "update student set name=? ,javaScore=?,htmlScore = ? ,cssScore = ? where id = ?";
		Object stuObj[] = new Object[] { student.getName(), student.getJavaScore(), student.getHtmlScore(),
				student.getCssScore(), student.getId() };
		return jdbcTemplate.update(sql, stuObj) == 1;
	}

	/**
	 * �����ܳɼ�ǰn��ѧ��
	 *
	 * @param num
	 * @return ����ֵ���ͣ� List<Student>
	 * @author janinus
	 */
	public List<Student> topNum(int num) {
		String sql = "select id,name,javaScore+htmlScore+cssScore from student order by javaScore+htmlScore+cssScore desc ,name asc limit ?";
		return jdbcTemplate.query(sql, new RowMapper<Student>() {

			@Override
			public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Student student = new Student();
				student.setId(rs.getInt(1));
				student.setName(rs.getString(2));
				student.setTotalScore(rs.getDouble(3));
				return student;
			}
		}, num);
	}

	/**
	 *
	 * StudentMapper���ݿ�ӳ��
	 *
	 * @ClassName StudentMapper
	 * @author janinus
	 * @date 2017��6��27��
	 * @Version V1.0
	 */

	class StudentMapper implements RowMapper<Student> {

		@Override
		public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Student student = new Student();
			student.setId(rs.getInt(1));
			student.setName(rs.getString(2));
			student.setJavaScore(rs.getDouble(3));
			student.setHtmlScore(rs.getDouble(4));
			student.setCssScore(rs.getDouble(5));

			return student;
		}

	}
}
