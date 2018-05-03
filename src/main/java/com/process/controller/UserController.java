package com.process.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.process.entity.User;
import com.process.service.UserService;

@Controller("/mine")
public class UserController {
	protected static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Resource
	private UserService userService;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private ObjectMapper objectMapper;

	@RequestMapping("/user")
	public ModelAndView getIndex() {
		ModelAndView mav = new ModelAndView("index");
		User user = userService.selectUserById(1);
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(
			value = {"/model/{modelId}/json"},
			method = {RequestMethod.GET},
			produces = {"application/json"}
	)
	public ObjectNode getEditorJson(@PathVariable String modelId) {
		ObjectNode modelNode = null;
		Model model = this.repositoryService.getModel(modelId);
		if(model != null) {
			try {
				if(StringUtils.isNotEmpty(model.getMetaInfo())) {
					modelNode = (ObjectNode)this.objectMapper.readTree(model.getMetaInfo());
				} else {
					modelNode = this.objectMapper.createObjectNode();
					modelNode.put("name", model.getName());
				}

				modelNode.put("modelId", model.getId());
				ObjectNode e = (ObjectNode)this.objectMapper.readTree(new String(this.repositoryService.getModelEditorSource(model.getId()), "utf-8"));
				modelNode.put("model", e);
			} catch (Exception var5) {
				LOGGER.error("Error creating model JSON", var5);
				throw new ActivitiException("Error creating model JSON", var5);
			}
		}
		return modelNode;
	}
	
}
