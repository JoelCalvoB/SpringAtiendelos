package com.entrecodigos.controllers;
import com.entrecodigos.daoimp.StripeClient;
import com.stripe.model.Charge;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    
    
    @Autowired
    @Qualifier("serviceDaoImp") 
    private StripeClient stripeClient;

    
    

    @PostMapping("/{empresa}/payment/charge")
    public Object chargeCard(@RequestBody Map<String,Object> respuesta,@PathVariable("empresa") String empresa) throws Exception {
        String token = String.valueOf(respuesta.get("token"));
        Double amount = Double.parseDouble(String.valueOf(respuesta.get("amount")));
        Charge t1 = this.stripeClient.chargeNewCard(token, amount , empresa);
        
        String json = t1.toJson();
        
       
        return json;
    }
}