/*
 * Copyright 2017 ~ 2025 the original author or authors. <wanglsir@gmail.com, 983708408@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wl4g.devops.ci.pipeline.provider;

import com.wl4g.components.core.bean.ci.Pipeline;
import com.wl4g.components.core.bean.ci.Project;
import com.wl4g.components.core.bean.ci.Trigger;
import com.wl4g.devops.ci.bean.PipelineModel;
import com.wl4g.devops.ci.core.PipelineManager;
import com.wl4g.devops.ci.core.context.PipelineContext;
import com.wl4g.devops.ci.core.param.RunParameter;
import com.wl4g.devops.ci.service.ProjectService;
import com.wl4g.devops.ci.service.TriggerService;
import com.wl4g.devops.vcs.operator.VcsOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.wl4g.components.common.lang.Assert2.hasText;

/**
 * Timing pipeline jobs provider.
 *
 * @author Wangl.sir <983708408@qq.com>
 * @author vjay
 * @date 2019-08-19 10:41:00
 */
public class TimingPipelineProvider extends AbstractPipelineProvider implements Runnable {
	final protected Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	protected PipelineManager pipeManager;
	@Autowired
	protected TriggerService triggerService;
	@Autowired
	protected ProjectService projectService;

	protected final Pipeline pipeline;

	protected Trigger trigger;

	public TimingPipelineProvider(Trigger trigger, Pipeline pipeline) {
		super(PipelineContext.EMPTY);
		this.trigger = trigger;
		this.pipeline = pipeline;
	}

	@Override
	public void run() {

		trigger = triggerService.getById(trigger.getId());
		Project project = projectService.getByAppClusterId(pipeline.getClusterId());

		// Gets VCS operator.
		VcsOperator operator = vcsManager.forOperator(project.getVcs().getProviderKind());
		try {
			/*if (!checkCommittedChanged(operator)) { // Changed?
				log.info("Skip timing tasks pipeline, because commit unchanged, with project:{}, trigger:{}", project, trigger);
				return;
			}*/

			// Creating pipeline task.
			// TODO traceId???
			PipelineModel pipeModel = flowManager.buildPipeline(pipeline.getId());
			pipeManager.runPipeline(new RunParameter(pipeline.getId(), "rollback", "1", "1", null), pipeModel);

			// set new sha in db
			String projectDir = config.getProjectSourceDir(project.getProjectName()).getAbsolutePath();
			String latestSha = operator.getLatestCommitted(projectDir);
			hasText(latestSha, "Trigger latest sha can't be empty for %s", projectDir);

			// Update latest sign.
			triggerService.updateSha(trigger.getId(), latestSha);
			trigger.setSha(latestSha);

			log.info("Timing pipeline tasks executed successful, with triggerId: {}, projectId: {}, projectName: {} ",
					trigger.getId(), project.getId(), project.getProjectName());
		} catch (Exception e) {
			log.error("", e);
		}

	}

	/**
	 * Check for updates. </br>
	 * When the Sha of the VCS local warehouse is different from the latest Sha
	 * on the server, it indicates that there is code update
	 * 
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	//TODO check is need update
	/*private boolean checkCommittedChanged(VcsOperator operator,Project project) throws Exception {
		String projectDir = config.getProjectSourceDir(project.getProjectName()).getAbsolutePath();
		if (operator.hasLocalRepository(projectDir)) {
			operator.checkoutAndPull(project.getVcs(), projectDir, task.getBranchName(), VcsAction.safeOf(task.getBranchType()));
		} else {
			operator.clone(project.getVcs(), project.getHttpUrl(), projectDir, task.getBranchName());
		}
		String newSign = operator.getLatestCommitted(projectDir);
		return !equalsIgnoreCase(trigger.getSha(), newSign);
	}*/

}