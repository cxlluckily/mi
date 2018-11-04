package com.shankephone.mi.security.shiro;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

public class CustomSimpleAuthorizationInfo extends SimpleAuthorizationInfo {
	
	private static final long serialVersionUID = 1L;
	private Set<Long> workSections;
	private Set<Long> warehouses;
	public Set<Long> getWorkSections() {
		return workSections;
	}
	public void setWorkSections(Set<Long> workSections) {
		this.workSections = workSections;
	}
	public Set<Long> getWarehouses() {
		return warehouses;
	}
	public void setWarehouses(Set<Long> warehouses) {
		this.warehouses = warehouses;
	}
	
	public void addWorkSections(Collection<Long> workSections) {
        if (this.workSections == null) {
            this.workSections = new HashSet<Long>();
        }
        this.workSections.addAll(workSections);
    }
	
	public void addWarehouses(Collection<Long> warehouses) {
        if (this.warehouses == null) {
            this.warehouses = new HashSet<Long>();
        }
        this.warehouses.addAll(warehouses);
    }
}
