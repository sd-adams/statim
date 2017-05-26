package jgirlapps.io.receiveorderservice.models;

import java.util.Date;
import java.util.List;

public class Order {
	String id;
	String agencyId;
	String reporterId;
	String agencyRatingId;
	String reporterRatingId;
	Date dateRequired;
	AddressType address;
	ResourceEnumType resourceType;
	SpecialtyEnumType specialtyType;
	OrderStatusEnumType orderStatus;
	String specialRequirements;
	List<IncentiveEnumType> incentives;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	public String getReporterId() {
		return reporterId;
	}
	public void setReporterId(String reporterId) {
		this.reporterId = reporterId;
	}
	public String getAgencyRatingId() {
		return agencyRatingId;
	}
	public void setAgencyRatingId(String agencyRatingId) {
		this.agencyRatingId = agencyRatingId;
	}
	public String getReporterRatingId() {
		return reporterRatingId;
	}
	public void setReporterRatingId(String reporterRatingId) {
		this.reporterRatingId = reporterRatingId;
	}
	public Date getDateRequired() {
		return dateRequired;
	}
	public void setDateRequired(Date dateRequired) {
		this.dateRequired = dateRequired;
	}
	public AddressType getAddress() {
		return address;
	}
	public void setAddress(AddressType address) {
		this.address = address;
	}
	public ResourceEnumType getResourceType() {
		return resourceType;
	}
	public void setResourceType(ResourceEnumType resourceType) {
		this.resourceType = resourceType;
	}
	public SpecialtyEnumType getSpecialtyType() {
		return specialtyType;
	}
	public void setSpecialtyType(SpecialtyEnumType specialtyType) {
		this.specialtyType = specialtyType;
	}
	public OrderStatusEnumType getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatusEnumType orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getSpecialRequirements() {
		return specialRequirements;
	}
	public void setSpecialRequirements(String specialRequirements) {
		this.specialRequirements = specialRequirements;
	}
	public List<IncentiveEnumType> getIncentives() {
		return incentives;
	}
	public void setIncentives(List<IncentiveEnumType> incentives) {
		this.incentives = incentives;
	}
	
	
	
}
