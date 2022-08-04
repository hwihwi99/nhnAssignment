package com.nhn.commerce.controller

import com.nhn.commerce.model.ModifyProduct
import com.nhn.commerce.model.NewProduct
import com.nhn.commerce.model.Product
import com.nhn.commerce.service.ProductService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.Optional

@Controller
class ProductController(
    private val productService: ProductService,
) {

    @GetMapping("/")
    fun home():String{
        println("홈 컨트롤러 시작")
        return "home"
    }
    @GetMapping("/addProduct")
    fun addProduct(model: Model, newProduct: NewProduct):String{
        model.addAttribute("newProduct",newProduct)
        return "addProduct"
    }
    @GetMapping("/product")
    fun getProductList(model: Model): String {
        log.error("LogNCrash Error Test")
        model.addAttribute("productList", productService.findProductList())
        return "product"
    }

    // TODO (상품 상세 조회 기능 + Exception 처리) 성공
    /**
     * productNo를 기준으로 상품 상세 조회하기
     * 만약 없으면 null값 전송 후 html에서 처리하기
     * */
    @GetMapping("/product/{productNo}")
    fun getProductDetail(model: Model, @PathVariable(name = "productNo") productNo:Int):String{
        println("디테일찾기 시작")
        var product:Product = productService.getDetail(productNo)
        model.addAttribute("productDetail", product)
        return "productDetail"
    }

    // TODO (상품 추가 기능) 성공
    /**
     * 만약 가격이 숫자가 아니라면 오류처리
     *
     * 시간남으면 리디렉션 처리도 하기
     * */
    @PostMapping("/addition/product")
    fun postProduct(newProduct: NewProduct) : String {
        println(newProduct.productName)
        println(newProduct.salePrice)
        productService.saveProduct(newProduct)
        println("상품등록 성공!")
        return "home";
    }

    // TODO (상품 수정 기능 + Exception 처리)
    /**
     * patch메소드를 사용하고 싶은데 post로 처리함.. patchMapping 사용하는 법 있나..?
     * 없는 상품은 수정 불가..
     * */
    @GetMapping("/product/modify/{productNo}")
    fun patchPageProduct(@PathVariable("productNo") productNo: Int, model: Model, modifyProduct:ModifyProduct) : String {
        println("수정 고고씽")
        val oldProduct:Product = productService.getDetail(productNo);
        if(oldProduct == null) {
            println("없는 상품 번호 입니다.")
            return "home"
        }
        model.addAttribute("oldProductInfo",oldProduct)
        model.addAttribute("newProductInfo",modifyProduct)
        return "modifyProduct"
    }

    @PostMapping("/modify/product/{productNo}")
    fun patchProduct(@PathVariable("productNo") productNo: Int,modifyProduct:ModifyProduct):String {
        println("진짜 수정한다잉")
        println(modifyProduct.productName)
        println(modifyProduct.salePrice)
        productService.updateProduct(productNo, modifyProduct)
        return "home"
    }

    // TODO (상품 삭제 기능 + Exception 처리)
    @GetMapping("/product/delete/{productNo}")
    fun deletePageProduct(@PathVariable("productNo") productNo: Int) : String {
        println("삭제 고고씽")
        val oldProduct:Product = productService.getDetail(productNo);
        if(oldProduct == null) {
            println("없는 상품 번호 입니다.")
            return "home"
        }
        productService.deleteProduct(productNo)
        return "home"
    }
}
