package com.ultrapower.wfinterface.core.service;

import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.wfinterface.core.model.WfiDatain;
import com.ultrapower.wfinterface.core.model.WorkflowContext;

/**
 * <p>为工单接口Web服务提供的处理工单的服务。此服务将从Web服务到操作工单API的整个操作过程封装起来，调用的入口为call方法。<br />
 * 每一个工单接口Web服务方法都需要创建一个独立的类，并继承抽象类{@link AbsWorkflowService}。<p />
 * <p>
 * 整个功能的工作流程是这样的（其中带*的都为实现类中编写的逻辑）：
 * <pre>
 *   1. 初始化阶段：
 *     1) 将WfiDatain对象保存到数据库中；
 *     2) 将传入参数的opDetail接消息成DataTable对象；
 *     3) *初始化（实现类扩展init()方法，一般进行接口传入信息的校验）；
 *   2. 数据处理阶段：
 *     1) 初始化WorkflowContext对象；
 *     2) *处理数据（实现类扩展dataHandle()方法，一般进行调用API数据的整理，如建单人、派发人以及流程所需的字段等）；
 *   3. API调用阶段：
 *     1) 封装API参数；
 *     2) 调用工单API；
 *   4. 结束阶段：
 *     1) *调用API后的处理（实现类扩展postHandle()方法，一般进行后续操作，比如说再调用一次接口或者发短信之类的逻辑）；
 *     2) 更新WfiDatain对象到数据库中。
 *     </pre>
 * 扩展类（init() dataHandle() postHandle()）的返回参数都为boolean，如果返回true，则继续操作；如果返回false，后续内容不执行并返回。
 * </p>
 * 注：实例化该类时不需要进行Spring的注入，直接new就可以，扩展类中的对象则需要通过WebApplicationManager.getBean(name)获得。
 * 
 * @see AbsWorkflowService
 * @author BigMouse
 *
 */
public interface IWorkflowService
{
	/**
	 * 返回null标识处理成功，否则返回错误信息
	 * @param serviceContext
	 * @return
	 */
	public String call(WfiDatain serviceContext) throws Exception;
	
	/**
	 * 用于被其它接口调用，可能其它接口调用逻辑会附加操作这张工单，这时，那个接口程序new这个对象，并调用extendCall方法
	 * @param serviceContext
	 * @return
	 * @throws Exception
	 */
	public String extendCall(DataTable fields, List<Attachment> attachments) throws Exception;
	
	/**
	 * 获取操作工单的上下文信息
	 * @return 操作工单的上下文信息
	 */
	public List<WorkflowContext> getWorkflowContexts();
}
