package com.nhn.commerce.service

import com.nhn.commerce.model.ModifyProduct
import com.nhn.commerce.model.NewProduct
import com.nhn.commerce.model.Product
import com.nhn.commerce.repository.ProductRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Optional

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {
    fun findProductList(): List<Product> = productRepository.findProductList()

    fun getDetail(productNo:Int):Product = productRepository.getDetailProduct(productNo)
    fun saveProduct(newProduct: NewProduct) {
        try{
            val inputNewProduct = newProduct.let {
                "{${newProduct.productName} and ${newProduct.salePrice}}"
            }
            println("새로받은 입력 값 확인하기")
            println(inputNewProduct)
            println("----------------------------")

            val productPrice:Int = newProduct.salePrice.toInt()
            if(productPrice.isNonPositive())
                throw Exception("가격은 양수여야 합니다.")

            productRepository.insertNewProduct(newProduct.productName, LocalDateTime.now(), productPrice, LocalDateTime.now())
        } catch (e:java.lang.NumberFormatException) {
            throw Exception("Not Number : 가격 입력값은 숫자로 이뤄져야 합니다.")
        }
    }

    fun updateProduct(productNo: Int, modifyProduct: ModifyProduct) {
        try{
            val inputModifyProduct = modifyProduct.let {
                "{${modifyProduct.productName} and ${modifyProduct.salePrice}}"
            }
            println("수정할 입력 값 확인하기")
            println(inputModifyProduct)
            println("----------------------------")

            val productPrice:Int = modifyProduct.salePrice.toInt();
            if(productPrice.isNonPositive())
                throw Exception("가격은 양수여야 합니다.")

            productRepository.updateProduct(productNo, modifyProduct.productName, productPrice)
        } catch (e:java.lang.NumberFormatException) {
            throw Exception("Not Number : 가격 입력값은 숫자로 이뤄져야 합니다.")
        }
    }

    fun Int.isNonPositive() : Boolean = this < 0;
    fun deleteProduct(productNo: Int) = productRepository.deleteProduct(productNo);
}
