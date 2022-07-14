package com.nhn.commerce.repository

import com.nhn.commerce.model.Product
import org.apache.ibatis.annotations.*
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.Optional

/**
 * 이게 MyBatis임 (처음에는 마이해빗인줄 알고 진짜 무슨 신기술인줄 알았다...ㅋㅋㅋ)
 * 이거를 사용하면 쿼리문이랑 해서 같이 쉽게 사용가능하다던데 이왕 이렇게 한거 그냥 정리한번 해보기.
 * */
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
    fun insertNewProduct(productName:String = "기본값 입력", registerYmdt:LocalDateTime = LocalDateTime.now(), salePrice:Int = 0, updateYmdt:LocalDateTime = LocalDateTime.now())

    @Update("update product set productName = #{productName}, salePrice = #{salePrice}, updateYmdt = now() where productNo =#{productNo}")
    fun updateProduct(productNo: Int, productName: String = "기본값 수정", salePrice: Int = 0)

    @Delete("delete from product where productNo = #{productNo}")
    fun deleteProduct(productNo: Int)
}
