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
            val productPrice:Int = newProduct.salePrice.toInt();
            productRepository.insertNewProduct(newProduct.productName, LocalDateTime.now(), productPrice, LocalDateTime.now())
        } catch (e:java.lang.NumberFormatException) {
            println("Not Number")
        }
    }

    fun updateProduct(productNo: Int, modifyProduct: ModifyProduct) {
        try{
            val productPrice:Int = modifyProduct.salePrice.toInt();
            productRepository.updateProduct(productNo, modifyProduct.productName, productPrice)
        } catch (e:java.lang.NumberFormatException) {
            println("Not Number")
        }
    }

    fun deleteProduct(productNo: Int) = productRepository.deleteProduct(productNo);
}
