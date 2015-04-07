package org.apache.ranger.view;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import org.apache.ranger.entity.XXGroupPermission;
import org.apache.ranger.entity.XXUserPermission;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonAutoDetect(getterVisibility=Visibility.NONE, setterVisibility=Visibility.NONE, fieldVisibility=Visibility.ANY)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL )
@JsonIgnoreProperties(ignoreUnknown=true)
@XmlRootElement

public class VXModuleDef extends VXDataObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected Long id;
	protected Date createTime;
	protected Date updateTime;
	protected Long addedById;
	protected Long updatedById;
	protected String module;
	protected String url;

	protected List<VXUserPermission> userPermList;
	protected List<VXGroupPermission> groupPermList;

	/**
	 * @return the userPermList
	 */
	public List<VXUserPermission> getUserPermList() {
		return userPermList;
	}
	/**
	 * @param userPermList the userPermList to set
	 */
	public void setUserPermList(List<VXUserPermission> userPermList) {
		this.userPermList = userPermList;
	}
	/**
	 * @return the groupPermList
	 */
	public List<VXGroupPermission> getGroupPermList() {
		return groupPermList;
	}
	/**
	 * @param groupPermList the groupPermList to set
	 */
	public void setGroupPermList(List<VXGroupPermission> groupPermList) {
		this.groupPermList = groupPermList;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the addedById
	 */
	public Long getAddedById() {
		return addedById;
	}
	/**
	 * @param addedById the addedById to set
	 */
	public void setAddedById(Long addedById) {
		this.addedById = addedById;
	}
	/**
	 * @return the updatedById
	 */
	public Long getUpdatedById() {
		return updatedById;
	}
	/**
	 * @param updatedById the updatedById to set
	 */
	public void setUpdatedById(Long updatedById) {
		this.updatedById = updatedById;
	}
	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}
	/**
	 * @param module the module to set
	 */
	public void setModule(String module) {
		this.module = module;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {

		String str = "VXModuleDef={";
		str += super.toString();
		str += "id={" + id + "} ";
		str += "createTime={" + createTime + "} ";
		str += "updateTime={" + updateTime + "} ";
		str += "addedById={" + addedById + "} ";
		str += "updatedById={" + updatedById + "} ";
		str += "module={" + module + "} ";
		str += "url={" + url + "} ";
		str += "}";
		return str;
	}
}
