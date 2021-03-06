package com.sidharth.demo.springcloud.ServiceImpl;

import com.sidharth.demo.springcloud.SpringcloudApplication;
import com.sidharth.demo.springcloud.core.dto.PriceDTO;
import com.sidharth.demo.springcloud.core.model.Price;
import com.sidharth.demo.springcloud.core.repo.PriceRepo;
import com.sidharth.demo.springcloud.core.repo.StocksRepo;
import com.sidharth.demo.springcloud.core.service.PriceService;
import com.sidharth.demo.springcloud.core.service.StockService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** 
* PriceServiceImpl Tester. 
* 
* @author <Sidharth Dash>
* @since <pre>Feb 23, 2018</pre> 
* @version 1.0 
*/


@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringcloudApplication.class)
public class PriceServiceImplTest {



    @Autowired
    PriceService priceService;

    @Autowired
    StockService stockService;

    @Autowired
    PriceRepo priceRepo;


    @Autowired
    StocksRepo stocksRepo;



@Before
public void before() throws Exception {

    priceRepo.deleteAll();
    stocksRepo.deleteAll();

    stocksRepo.createStocks("IBM","IBM");
    stocksRepo.createStocks("Cognizant","Cog");


    priceRepo.createPrice(stocksRepo.findStocksByStockCode("IBM").getId(),10.0);
    Thread.sleep(1000);
    priceRepo.createPrice(stocksRepo.findStocksByStockCode("IBM").getId(),20.0);
    Thread.sleep(1000);
    priceRepo.createPrice(stocksRepo.findStocksByStockCode("IBM").getId(),30.0);
    Thread.sleep(1000);
    priceRepo.createPrice(stocksRepo.findStocksByStockCode("Cog").getId(),40.0);
    Thread.sleep(1000);
    priceRepo.createPrice(stocksRepo.findStocksByStockCode("Cog").getId(),50.0);
    Thread.sleep(1000);
}

@After
public void after() throws Exception {
    priceRepo.deleteAll();
    stocksRepo.deleteAll();


}

/**
*
* Method: getLatestPrices(long stockId)
*
*/
@Test
public void testGetLatestPrices() throws Exception {
//TODO: Test goes here...


    assertEquals(priceService.getLatestPrices(stocksRepo.findStocksByStockCode("IBM").getId()).getPrice(),30.0);
    assertEquals(priceService.getLatestPrices(stocksRepo.findStocksByStockCode("Cog").getId()).getPrice(),50.0);
    assertEquals(0,priceService.getLatestPrices(123).getPrice());
    assertEquals(0,priceService.getLatestPrices(3213).getPrice());

}

/**
*
* Method: getAllPrices(long stockId)
*
*/
@Test
public void testGetAllPrices() throws Exception {
    assertEquals(priceService.getAllPrices(stocksRepo.findStocksByStockCode("IBM").getId()).size(),3);
    assertEquals(priceService.getAllPrices(stocksRepo.findStocksByStockCode("Cog").getId()).size(),2);
    assertEquals(0,priceService.getLatestPrices(123).getPrice());
    assertEquals(0,priceService.getLatestPrices(3213).getPrice());
}

/**
*
* Method: getLimitedPrices(long stockId, int numberOfEntries)
*
*/
@Test
public void testGetLimitedPrices() throws Exception {
    assertEquals(priceService.getLimitedPrices(stocksRepo.findStocksByStockCode("IBM").getId(),2).size(),2);
    assertEquals(priceService.getLimitedPrices(stocksRepo.findStocksByStockCode("IBM").getId(),3).size(),3);
    assertEquals(priceService.getLimitedPrices(stocksRepo.findStocksByStockCode("IBM").getId(),4).size(),3);
    assertEquals(priceService.getLimitedPrices(stocksRepo.findStocksByStockCode("Cog").getId(),2).size(),2);
    assertTrue(priceService.getLimitedPrices(213423,4).isEmpty());
    assertTrue(priceService.getLimitedPrices(123412,2).isEmpty());

}

/**
*
* Method: updatePrices(PriceDTO priceDTO, long stockId)
*
*/
@Test
public void testUpdatePrices() throws Exception {
    PriceDTO priceDTO = new PriceDTO();
    priceDTO.setPrice(60.0);
    assertEquals(priceService.updatePrices(priceDTO,1).getPrice(),60.0);

    priceDTO.setPrice(70.0);
    assertEquals(priceService.updatePrices(priceDTO,1).getPrice(),70.0);
}

/**
*
* Method: getPricesBetweenTime(long startTime, long endTime, long stockId)
*
*/
@Test
public void testGetPricesBetweenTime() throws Exception {


}


/**
*
* Method: priceEntityToDTO(Price price)
*
*/
@Test
public void testPriceEntityToDTO() throws Exception {
    Price price= new Price();
    price.setId((long)1);
    price.setPrice(22.0);
    PriceDTO priceDTO=priceService.priceEntityToDTO(price);
    assertEquals(priceDTO.getPrice(),price.getPrice());
    priceDTO=priceService.priceEntityToDTO(null);
    assertEquals(0,priceDTO.getPrice());

}

/**
*
* Method: priceDTOtoEntity(PriceDTO priceDTO)
*
*/
@Test
public void testPriceDTOtoEntity() throws Exception {
    PriceDTO priceDTO = new PriceDTO();
    priceDTO.setPrice(10.0);
    Price price = priceService.priceDTOtoEntity(priceDTO);
    assertEquals(price.getPrice(),priceDTO.getPrice());
    price=priceService.priceDTOtoEntity(null);
    assertEquals(0,price.getPrice());

}

/**
*
* Method: priceEntityListToDTOList(List<Price> priceList)
*
*/
@Test
public void testPriceEntityListToDTOList() throws Exception {
    Price price1= new Price();
    price1.setId((long)1);
    price1.setPrice(21.0);
    Price price2= new Price();
    price2.setId((long)2);
    price2.setPrice(22.0);
    Price price3= new Price();
    price3.setId((long)3);
    price3.setPrice(23.0);
    List<Price> priceList = new ArrayList<>();
    priceList.add(price1);
    priceList.add(price2);
    priceList.add(price3);

    List<PriceDTO> priceDTOList = priceService.priceEntityListToDTOList(priceList);
    assertEquals(price1.getPrice(),priceDTOList.get(0).getPrice());
    assertEquals(price2.getPrice(),priceDTOList.get(1).getPrice());
    assertEquals(price3.getPrice(),priceDTOList.get(2).getPrice());
    assertEquals(priceList.size(),priceDTOList.size());
    priceDTOList = priceService.priceEntityListToDTOList(null);

    assertTrue(priceDTOList.isEmpty());



}

} 
