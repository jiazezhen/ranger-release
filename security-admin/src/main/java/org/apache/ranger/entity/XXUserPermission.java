package org.apache.ranger.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.ranger.common.AppConstants;
import org.apache.ranger.common.RangerCommonEnums;
import com.sun.research.ws.wadl.Application;

@Entity
@Table(name = "x_user_module_perm")
@XmlRootElement
public class XXUserPermission extends XXDBBase implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "X_USER_MODULE_PERM_SEQ", sequenceName = "X_USER_MODULE_PERM_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "X_USER_MODULE_PERM_SEQ")
	@Column(name = "ID")
	protected Long id;

	@Column(name = "USER_ID", nullable = false)
	protected Long userId;

	@Column(name = "MODULE_ID", nullable = false)
	protected Long moduleId;

	@Column(name = "IS_ALLOWED", nullable = false)
	protected Integer isAllowed;

	public XXUserPermission(){
		isAllowed = RangerCommonEnums.IS_ALLOWED;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the moduleId
	 */
	public Long getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleId
	 *            the moduleId to set
	 */
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * @return the isAllowed
	 */
	public Integer getIsAllowed() {
		return isAllowed;
	}

	/**
	 * @param isAllowed
	 *            the isAllowed to set
	 */
	public void setIsAllowed(Integer isAllowed) {
		this.isAllowed = isAllowed;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		XXUserPermission other = (XXUserPermission) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isAllowed == null) {
			if (other.isAllowed != null)
				return false;
		} else if (!isAllowed.equals(other.isAllowed))
			return false;
		if (moduleId == null) {
			if (other.moduleId != null)
				return false;
		} else if (!moduleId.equals(other.moduleId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public int getMyClassType() {
		return AppConstants.CLASS_TYPE_RANGER_USER_PERMISSION;
	}

	@Override
	public String toString() {

		String str = "VXUserPermission={";
		str += super.toString();
		str += "id={" + id + "} ";
		str += "userId={" + userId + "} ";
		str += "moduleId={" + moduleId + "} ";
		str += "isAllowed={" + isAllowed + "} ";
		str += "}";

		return str;
	}
}
