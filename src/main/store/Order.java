package store;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order {

	private Customer customer;
	private Salesman salesman;
	private Date orderedOn;
	private String deliveryStreet;
	private String deliveryCity;
	private String deliveryCountry;
	private Set<OrderItem> items;

	public Order(Customer customer, Salesman salesman, String deliveryStreet, String deliveryCity, String deliveryCountry, Date orderedOn) {
		this.customer = customer;
		this.salesman = salesman;
		this.deliveryStreet = deliveryStreet;
		this.deliveryCity = deliveryCity;
		this.deliveryCountry = deliveryCountry;
		this.orderedOn = orderedOn;
		this.items = new HashSet<OrderItem>();
	}

	public Customer getCustomer() {
		return customer;
	}

	public Salesman getSalesman() {
		return salesman;
	}

	public Date getOrderedOn() {
		return orderedOn;
	}

	public String getDeliveryStreet() {
		return deliveryStreet;
	}

	public String getDeliveryCity() {
		return deliveryCity;
	}

	public String getDeliveryCountry() {
		return deliveryCountry;
	}

	public Set<OrderItem> getItems() {
		return items;
	}

	public float total() {
		float totalItemsPrice = 0;
		for (OrderItem item : items) {
			float itemPrice=0;
			float itemAmount = item.getProduct().getUnitPrice() * item.getQuantity();
			itemPrice = getOrderCost(item, itemPrice, itemAmount);
			totalItemsPrice += itemPrice;
		}


		totalItemsPrice = applyTaxing(totalItemsPrice, this.deliveryCountry);

		return totalItemsPrice;
	}

	private float applyTaxing(float totalItemsPrice, String deliveryCountry){
		if (deliveryCountry == "USA"){
			// total=totalItemsPrice + tax + 0 shipping
			totalItemsPrice = totalItemsPrice + totalItemsPrice * 5 / 100;
		} else{
			totalItemsPrice = totalItemsPrice + totalItemsPrice * 5 / 100 + 15;
		}
		return totalItemsPrice;
	}

	private float getOrderCost(OrderItem item, float itemPrice, float itemAmount){
		if (item.getProduct().getCategory() == ProductCategory.Cloathing)
			itemPrice = getCloathingCost(item, itemPrice, itemAmount);
		if (item.getProduct().getCategory() == ProductCategory.Bikes)
			itemPrice = getBikeCost(item, itemPrice, itemAmount);
		if (item.getProduct().getCategory() == ProductCategory.Accessories)
			itemPrice = getAccesoryCost(item, itemPrice, itemAmount);
		return itemPrice;
	}

	private float getCloathingCost(OrderItem item, float itemPrice, float itemAmount) {
			float cloathingDiscount = 0;
			if (item.getQuantity() > 2) {
				cloathingDiscount = item.getProduct().getUnitPrice();
			}
			itemPrice = itemAmount - cloathingDiscount;
		return itemPrice;
	}

	private float getBikeCost(OrderItem item, float itemPrice, float itemAmount) {

			// 20% discount for Bikes
			itemPrice = itemAmount - itemAmount * 20 / 100;
		return itemPrice;
	}

	private float getAccesoryCost(OrderItem item, float itemPrice, float itemAmount) {
			float booksDiscount = 0;
			if (itemAmount >= 100) {
				booksDiscount = itemAmount * 10 / 100;
			}
			itemPrice = itemAmount - booksDiscount;
		return itemPrice;
	}
}
