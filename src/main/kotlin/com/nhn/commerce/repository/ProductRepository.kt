package com.nhn.commerce.repository

import com.nhn.commerce.model.Product
import org.apache.ibatis.annotations.*
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.Optional

@Mapper
@Repository
interface ProductRepository {
    @Select("SELECT * FROM product")
    fun findProductList(): List<Product>

    @Select("select * from product where productNo=#{productNo}")
    fun getDetailProduct(productNo:Int):Product

    @Select("select last_insert_id() as productNo")
    fun getLastIndex() : Int

    @Insert("insert into product(productName, registerYmdt, salePrice,updateYmdt) value(#{productName},#{registerYmdt},#{salePrice},#{updateYmdt})")
    fun insertNewProduct(productName:String, registerYmdt:LocalDateTime, salePrice:Int, updateYmdt:LocalDateTime)

    @Update("update product set productName = #{productName}, salePrice = #{salePrice}, updateYmdt = now() where productNo =#{productNo}")
    fun updateProduct(productNo: Int, productName: String, salePrice: Int)

    @Delete("delete from product where productNo = #{productNo}")
    fun deleteProduct(productNo: Int)
}
