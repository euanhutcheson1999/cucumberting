package swag;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

public class MyStepdefs {
        WebDriver driver;

        @Given("^I add four different products to my wish list$")
        public void iAddFourDifferentProductsToMyWishList() throws InterruptedException {
                System.setProperty("webdriver.chrome.driver", "//Users//euanhutcheson//Documents//chromedriver");
                driver= new ChromeDriver();
                driver.get("https://testscriptdemo.com");
                driver.manage().window().setSize(new Dimension(1936, 1096));
                driver.findElement(By.xpath("//a[@href='?add_to_wishlist=17']")).click();
                Thread.sleep(400);
                driver.findElement(By.xpath("//a[@href='?add_to_wishlist=14']")).click();
                Thread.sleep(400);
                driver.findElement(By.xpath("//a[@href='?add_to_wishlist=20']")).click();
                Thread.sleep(400);
                driver.findElement(By.xpath("//a[@href='?add_to_wishlist=23']")).click();
                Thread.sleep(400);
        }

        @When("^I view my wishlist table$")
        public void iViewMyWishlistTable() {
                driver.findElement(By.xpath("//a[@href='https://testscriptdemo.com/?page_id=233&wishlist-action']")).click();
        }

        @Then("^I find total four selected items in my Wishlist$")
        public void iFindTotalFourSelectedItemsInMyWishlist() throws InterruptedException {
                        Thread.sleep(300);
                        List<WebElement> rows = driver.findElements(By.xpath("//table[@class='shop_table cart wishlist_table wishlist_view traditional responsive   ']/tbody/tr"));
                        assertEquals(4, rows.size(), "The numbers in wishlist were not 4");
                }

        @When("^I search for lowest price product$")
        public String iSearchForLowestPriceProduct() throws InterruptedException {
                        Thread.sleep(300);
                        String product_price;
                        String product_cart;
                        List<WebElement> price = driver.findElements(By.xpath("//td[@class='product-price']"));
                        List<WebElement> product = driver.findElements(By.xpath("//td[@class='product-add-to-cart']/a"));
                        Map<String, Double> WebsitePrice = new HashMap<String, Double>();
                        //System.out.println(product.get(0).getAttribute("data-product_id"));
                        //System.out.println(price.get(0).getText());
                        for (int i = 0; i < product.size(); i++) {
                                product_cart = product.get(i).getAttribute("href");
                                product_price = price.get(i).getText();
                                if (product_price.contains(" – ")) {
                                        product_price = product_price.substring(0, 5);
                                } else {
                                        product_price = product_price.substring(8);
                                }
                                product_price = product_price.replaceAll("£", "");
                                double double_product_price = Double.parseDouble(product_price);
                                WebsitePrice.put(product_cart, double_product_price);
                        }
                        Map.Entry<String, Double> minEntry = null;
                        for (Map.Entry<String, Double> entry : WebsitePrice.entrySet()) {
                                if (minEntry == null
                                        || entry.getValue().compareTo(minEntry.getValue()) < 0) {
                                        minEntry = entry;
                                }
                        }

                        String lowestPrice = minEntry.getKey();
                        return lowestPrice;
                }


        @And("^I am able to add the lowest price item to my cart$")
        public void iAmAbleToAddTheLowestPriceItemToMyCart() throws InterruptedException {
                String lowestPrice = iSearchForLowestPriceProduct();
                String link = lowestPrice.substring(27);
                Thread.sleep(300);
                driver.findElement(By.xpath("//a[@href='"+link+"']")).click();
        }






        @Then("^I am able to verify the item in my cart$")
        public void iAmAbleToVerifyTheItemInMyCart() throws InterruptedException {
                Thread.sleep(300);
                driver.findElement(By.xpath("//a[@href='https://testscriptdemo.com/?page_id=299']")).click();
                List<WebElement> rows = driver.findElements(By.xpath("//table[@class='shop_table shop_table_responsive cart woocommerce-cart-form__contents']"));
                assertEquals(1, rows.size(), "HAHAHAHAH WHADAJOKER");


        }}





