package com.jwd_admission.byokrut.tag;

import com.jwd_admission.byokrut.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

/**
 *This tag displays information from the list of users to the JSP page
 * */
public class ListOfPassedTag extends TagSupport {
    private List<User> userList;
    private String tdOpenTag = "<td>";
    private String tdCloseTag = "</td>";
    private String trOpenTag = "<tr>";
    private String trCloseTag = "</tr>";

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public int doStartTag() throws JspException {
        if (userList != null) {
            try {
                JspWriter out = pageContext.getOut();
                for (User user : userList) {
                    String name = user.getPersonalInformation().getFirstName();
                    String lastname = user.getPersonalInformation().getLastName();
                    out.println(trOpenTag + tdOpenTag + name + tdCloseTag + tdOpenTag + lastname + tdCloseTag + tdOpenTag + user.getPersonalInformation().getMiddleName() + tdCloseTag + tdOpenTag
                            + user.getPersonalInformation().getPassportId() + tdCloseTag + trCloseTag);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return EVAL_PAGE;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
