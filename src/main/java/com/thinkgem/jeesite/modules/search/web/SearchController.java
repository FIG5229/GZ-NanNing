package com.thinkgem.jeesite.modules.search.web;

import com.thinkgem.jeesite.common.elasticsearch.bean.EsAttachment;
import com.thinkgem.jeesite.common.elasticsearch.bean.EsThing;
import com.thinkgem.jeesite.common.elasticsearch.bean.PagerSearchResult;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.search.service.SearchService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * 一键云搜
 */

//@Controller
//@RequestMapping(value = "${adminPath}/search")
public class SearchController extends BaseController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "formSearchPeople", method = RequestMethod.GET)
    public ModelAndView formSearchPeople(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String searchContent = request.getParameter("search-content");

        ModelAndView view = new ModelAndView("modules/search/searchPeople");
        if (searchContent == null || searchContent.equals("")) {
            return view;
        }

        String searchCondition = WebUtils.getCleanParam(request, "condition");
        request.setAttribute("searchContent", searchContent);
        PagerSearchResult<PersonnelBase> personnelBasePage = searchService.conditionalSearchPeople(searchContent, searchCondition, 0, 10);

        view.addObject("personnelBasePage", personnelBasePage);

        return view;
    }

    @RequestMapping(value = "formSearchInstitution", method = RequestMethod.GET)
    public ModelAndView formSearchInstitution(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String searchContent = request.getParameter("search-content");
        String searchCondition = WebUtils.getCleanParam(request, "condition");

        ModelAndView view = new ModelAndView("modules/search/searchInstitution");
        if (searchContent == null || searchContent.equals("")) {
            return view;
        }

        request.setAttribute("searchContent", searchContent);
        PagerSearchResult<Office> officePagerSearchResult = searchService.conditionalSearchInstitution(searchContent, searchCondition, 0, 10);
        view.addObject("officePagerSearchResult", officePagerSearchResult);

        return view;
    }

    @RequestMapping(value = "formSearchFile", method = RequestMethod.GET)
    public ModelAndView formSearchFile(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String searchContent = request.getParameter("search-content");
        String searchCondition = WebUtils.getCleanParam(request, "condition");

        ModelAndView view = new ModelAndView("modules/search/searchFile");
        if (searchContent == null || searchContent.equals("")) {
            return view;
        }

        request.setAttribute("searchContent", searchContent);
        PagerSearchResult<EsAttachment> esAttachmentPagerSearchResult = searchService.conditionalSearchFile(searchContent, searchCondition, 0, 10);

        view.addObject("esAttachmentPagerSearchResult", esAttachmentPagerSearchResult);

        return view;
    }

    @RequestMapping(value = "formSearchThing", method = RequestMethod.GET)
    public ModelAndView formSearchThing(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String searchContent = request.getParameter("search-content");
        String searchCondition = WebUtils.getCleanParam(request, "condition");

        ModelAndView view = new ModelAndView("modules/search/searchThing");
        if (searchContent == null || searchContent.equals("")) {
            return view;
        }

        request.setAttribute("searchContent", searchContent);
        PagerSearchResult<EsThing> esThingPagerSearchResult = searchService.conditionalSearchThing(searchContent, searchCondition, 0, 10);

        view.addObject("esThingPagerSearchResult", esThingPagerSearchResult);

        return view;
    }

    @RequestMapping(value = "searchPeople")
    public String searchPeople(Model model) {
        return "modules/search/searchPeople";
    }

    @RequestMapping(value = "searchThing")
    public String searchThing(Model model) {
        return "modules/search/searchThing";
    }

    @RequestMapping(value = "searchStatistics")
    public String searchStatistics(Model model) {
        return "modules/search/searchStatistics";
    }

    @RequestMapping(value = "searchInstitution")
    public String searchInstitution(Model model) {
        return "modules/search/searchInstitution";
    }

    @RequestMapping(value = "searchFile")
    public String searchFile(Model model) {
        return "modules/search/searchFile";
    }
}
