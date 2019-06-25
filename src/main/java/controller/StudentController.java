package controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bean.Student;
import dao.StudentDao;

@Controller
public class StudentController {

    /**
     *
     * �����ݿ��л�ȡȫ��ѧ����Ϣ�������ݷ��ظ���ҳindex,jsp
     *
     * @param model
     * @return ����ֵ���ͣ� String
     * @author janinus
     */
    @RequestMapping(value = "/all")
    public String queryAll(Model model) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //��ioc�����л�ȡdao
        StudentDao dao = (StudentDao) context.getBean("dao");
        model.addAttribute("students", dao.queryAll());
        model.addAttribute("tops", dao.topNum(3));
        return "index.jsp";
    }

    /**
     * ͨ����������ѧ����ʹ��ģ�����ң���������ظ�index.jsp
     *
     * @param name
     * @param model
     * @return ����ֵ���ͣ� String
     * @author janinus
     */
    @RequestMapping(value = "/queryByName")
    public String queryByName(String name, Model model) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //��ioc�����л�ȡdao
        StudentDao dao = (StudentDao) context.getBean("dao");
        model.addAttribute("students", dao.queryByName(name));
        model.addAttribute("tops", dao.topNum(3));
        return "index.jsp";
    }

    /**
     * �����ѧ��������������ظ�allҳ�棬��allת������ҳ
     * @param name
     * @param javaScore
     * @param htmlScore
     * @param cssScore
     * @param model
     * @return ����ֵ���ͣ� String
     * @author janinus
     */
    @RequestMapping(value = "/add")
    public String addStu(String name, String javaScore, String htmlScore, String cssScore, Model model) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StudentDao dao = (StudentDao) context.getBean("dao");
        Student student = new Student();
        student.setName(name);
        student.setJavaScore(Double.parseDouble(javaScore));
        student.setHtmlScore(Double.parseDouble(htmlScore));
        student.setCssScore(Double.parseDouble(cssScore));
        boolean result = dao.addStu(student);
        if (result)
            model.addAttribute("msg", "<script>alert('��ӳɹ���')</script>");
        else
            model.addAttribute("msg", "<script>alert('��ӳɹ���')</script>");
        return "all";
    }

    /**
     * ͨ��idɾ��ѧ��
     * @param id
     * @param model
     * @return ����ֵ���ͣ� String
     * @author janinus
     */
    @RequestMapping(value = "/deleteById")
    public String deleteById(String id, Model model) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StudentDao dao = (StudentDao) context.getBean("dao");
        boolean result = dao.deleteStu(Integer.parseInt(id));
        if (result)
            model.addAttribute("msg", "<script>alert('ɾ���ɹ���')</script>");
        else
            model.addAttribute("msg", "<script>alert('ɾ���ɹ���')</script>");
        return "all";
    }

    /**
     *
     * @param id
     * @param name
     * @param javaScore
     * @param htmlScore
     * @param cssScore
     * @param model
     * @return ����ֵ���ͣ� String
     * @author janinus
     */
    @RequestMapping(value = "/update")
    public String updateStu(String id, String name, String javaScore, String htmlScore, String cssScore, Model model) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StudentDao dao = (StudentDao) context.getBean("dao");
        Student student = new Student();
        student.setId(Integer.parseInt(id));
        student.setName(name);
        student.setJavaScore(Double.parseDouble(javaScore));
        student.setHtmlScore(Double.parseDouble(htmlScore));
        student.setCssScore(Double.parseDouble(cssScore));
        boolean result = dao.updateStu(student);
        if (result)
            model.addAttribute("msg", msg("�޸ĳɹ�"));
        else
            model.addAttribute("msg", msg("�޸�ʧ��"));
        return "all";
    }

    /**
     * Ҫ������ҳ����Ϣ
     * @param msg
     * @return ����ֵ���ͣ� String
     * @author janinus
     */
    public String msg(String msg) {
        return "<script>alert('" + msg + "')</script>";
    }
}
