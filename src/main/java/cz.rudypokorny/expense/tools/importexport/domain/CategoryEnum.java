package cz.rudypokorny.expense.tools.importexport.domain;

import cz.rudypokorny.expense.model.Category;

import java.util.EnumSet;
import java.util.Set;

public enum CategoryEnum {

    //CATEGORY
    FOOD_SWEETS("Sweets & Salty"),
    FOOD_SNACKS("Snacks"),
    FOOD_GROCERIES("Groceries"),
    FOOD_RESTAURANT("Restaurant"),
    FOOD_DRINKS_GENERAL("Food & Drinks"),
    FOOD_WORK_LUNCH("Work lunch"),
    FOOD_BABY_FOOD("Baby food"),


    SHOPPING_GENERAL("General - Shopping"),
    SHOPPING_CLOTHES("Clothes"),
    SHOPPING_SHOES("Shoes"),
    SHOPPING_GRUGSTORE("Drugstore"),
    SHOPPING_ELECTRONICS("Electronics"),
    SHOPPING_PHONE_ACCESSORIES("Phone & Accessories"),
    SHOPPING_CAMERA_ACCESSORIES("Camera & Photo stuff"),
    SHOPPING_FREE_TIME("Free time"),
    SHOPPING_GIFTS("Gifts"),
    SHOPPING_CHRISTMAS_GIFTS("Christmas gifts"),
    SHOPPING_HEALTH_BEAUTY("Health & Beauty"),
    SHOPPING_HOME_GARDEN("Home, Garden"),
    SHOPPING_JEWELS_ACCESSORIES("Jewels, accessories"),
    SHOPPING_BABY("Baby"),
    SHOPPING_BABY_CLOTHES("Baby clothes"),

    HOUSING_GENERAL("General - Housing"),
    HOUSING_FURNITURE("Housing  improvements"),
    HOUSING_ENERGY("Housing energy, services"),
    HOUSING_REPAIRS("Housing repairs"),
    HOUSING_MORTGAGE("Mortgage"),
    HOUSING_INSURANCE("Property insurance"),
    HOUSING_RENT("Rent"),

    TRANSPORTATION("General - Transportation"),
    TRANSPORTATION_MHD("Public transport"),

    VEHICLE_GENERAL("Vehicle"),
    VEHICLE_LOAN("Loan"),
    VEHICLE_FUEL("Fuel"),
    VEHICLE_PARKING("Parking"),
    VEHICLE_INSURANCE("Vehicle insurance"),
    VEHICLE_MAINTENANCE("Vehicle maintenance"),

    LIFE_ENTERTAINMENT_GENERAL("Entertainment"),
    LIFE_SPORT("Active sport, fitness"),
    LIFE_PARTY("Party"),
    LIFE_BOOKS_AUDIO("Books, audio, subscriptions"),
    LIFE_CULTURE("Culture, sport events"),
    LIFE_EDUCATION("Education, development"),
    LIFE_HEALCARE("Health care, doctor"),
    LIFE_HEALCARE_BABY("Health care baby"),


    LIFE_WELLNESS_BEAUTY("Wellness, beauty"),
    LIFE_HOLIDAYS("Holiday, trips, hotels"),

    PC("Computer & Accessories"),
    INTERNET("Internet"),
    PHONE("Phone, mobile phone"),
    POSTAL("Postal services"),
    SOFTWARE("Software, apps, games"),

    FINANCIAL_EXPENSES("General - Financial expenses"),
    FINANCIAL_ADVISORY("Advisory"),
    FINANCIAL_FEES("Charges, Fees"),
    FINANCIAL_CHILD("Child Support"),
    FINANCIAL_FINES("Fines"),
    FINANCIAL_INSURANCES("Insurances"),
    FINANCIAL_LOANS("Loan, interests"),
    FINANCIAL_TAXES("Taxes"),

    INVESTMENTS_GENERAL("General - Investments"),
    INVESTMENTS__COLLECTIONS("Collections"),
    INVESTMENTS_FINANCIAL("Financial investments"),
    INVESTMENTS_REALTY("Realty"),
    INVESTMENTS_PENSION("Pension"),
    INVESTMENTS_VEHICLES("Vehicles, chattels"),
    INVESTMENTS_OTHERS("Investments - Others"),

    INCOME_GENERAL("General - Income"),
    INCOME_CHILD_SUPPORT("Child Support"),
    INCOME_COUPONS("Coupons"),
    INCOME_RENTAL("Rental income"),
    INCOME_WAGE("Wage"),

    INCOME_OTHER("Other income"),

    OTHERS("General - Others");

    private static String SPLIT = "-||-";
    private String name;
    private String subCategory;


    CategoryEnum(String name) {
        this.name = name;
    }

    public static Set<CategoryEnum> incomes() {
        return EnumSet.of(INCOME_GENERAL, INCOME_CHILD_SUPPORT, INCOME_COUPONS, INCOME_RENTAL, INCOME_WAGE, INCOME_OTHER);
    }

    public String getName() {
        return name;
    }

    public Category full() {
        return Category.named(name);
    }
}