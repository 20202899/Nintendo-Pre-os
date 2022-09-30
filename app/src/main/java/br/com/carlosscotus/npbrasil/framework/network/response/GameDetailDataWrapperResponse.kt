package br.com.carlosscotus.npbrasil.framework.network.response

import com.google.gson.annotations.SerializedName

data class GameDetailDataWrapperResponse(
    var pageProps: PageProps,
    var __N_SSP: Boolean
)

data class PageProps(
    var product: Product,
    var configurable: Any?,
    var locale: String,
    var preview: Boolean,
    var previewData: Any?,
    var meta: Meta,
    var analytics: Analytics,
    var openGraph: OpenGraph,
    var linkedData: LinkedData
)

data class Product(
    var __typename: String,
    var ageGate: Boolean,
    var appStoreUrl: String?,
    var availability: List<String>,
    var backgroundColor: String,
    var capacity: Any?,
    var configurableOptions: Any?,
    var containsBattery: Boolean,
    var contentDescriptors: List<Any>,
    var contentRating: ContentRating,
    var detailPageUrl: String,
    var displayChokingHazard: Boolean,
    var displayProp65: Boolean,
    var manufacturer: Any?,
    var countryOfManufacture: Any?,
    var countryOfOrigin: Any?,
    var demoNsuid: Any?,
    var demoType: Any?,
    var description: String,
    var descriptionImage: DescriptionImage,
    var disclaimer: String,
    var dlcType: Any?,
    var enableRetailCrawler: Boolean,
    var eshopDetails: EshopDetails,
    var exclusive: Boolean,
    var DetailFacets: DetailFacets,
    var googlePlayUrl: String?,
    var genres: List<Genre>,
    var headline: String,
    var isSalableQty: Boolean,
    var locale: String,
    var metaDescription: String,
    var metaKeyword: Any?,
    var metaTitle: String,
    var name: String,
    var nsoFeatures: List<NsoFeature>,
    var nsuid: String,
    var id: String,
    var numberOfPlayers: Any?,
    var officialSite: String,
    var productType: String,
    var productImage: ProductImage,
    var parentNsuid: String,
    var platform: Platform,
    var platinumPoints: Any?,
    var playModes: List<PlayMode>,
    var playersMax: Int,
    var playersMaxLocal: Int,
    var playersMaxOnline: Int,
    var playersMin: Int,
    var playersMinLocal: Int,
    var playersMinOnline: Int,
    var prePurchase: Boolean,
    var purchasableQty: Int,
    var maxQtyAllowedInCart: Int,
    var productCode: String,
    var productGallery: List<ProductGallery>,
    var prices: Prices,
    var qtyAllowedPerCustomer: Any?,
    var relatedProducts: List<Any>,
    var releaseDate: String,
    var releaseDateDisplay: Any?,
    var requiresLogin: Boolean,
    var requiresCoupon: Boolean,
    var requiresSubscription: Boolean,
    var romFileSize: String,
    var series: Any?,
    var size: Any?,
    var sizeChart: Any?,
    var stockStatus: String,
    var sku: String,
    var softwareDeveloper: String,
    var softwarePublisher: String,
    var soldOutTemporary: Boolean,
    var soldOutPermanent: Boolean,
    var supportedLanguages: List<String>,
    var switchRequired: Boolean,
    var topLegalDisclaimer: Any?,
    var topLevelCategory: TopLevelCategory,
    var upc: String,
    var urlKey: String,
    var variants: Any?
)

data class Meta(
    var title: String,
    var description: String
)

data class Analytics(
    var pageType: String,
    var pageName: String,
    var pageCategory: String,
    var product: ProductX
)

data class OpenGraph(
    var image: String
)

data class LinkedData(
    @SerializedName("@type")
    var type: String,
    var name: String,
    var image: String,
    var description: String,
    var brand: String,
    var category: String,
    var sku: String,
    var offers: Offers
)

data class ContentRating(
    var __typename: String,
    var id: String,
    var locale: String,
    var code: String,
    var label: String,
    var requiresAgeGate: Boolean,
    var system: String
)

data class DescriptionImage(
    var __typename: String,
    var publicId: String,
    var resourceType: String,
    var type: String
)

data class EshopDetails(
    var __typename: String,
    var isPurchased: Boolean,
    var isPreordered: Boolean,
    var isPreorderable: Boolean,
    var isPurchasable: Boolean,
    var goldPoints: Int,
    var purchaseUrl: String
)

data class DetailFacets(
    var __typename: String,
    var corePlatforms: List<String>
)

data class Genre(
    var __typename: String,
    var code: String,
    var label: String
)

data class NsoFeature(
    var __typename: String,
    var code: String,
    var label: String
)

data class ProductImage(
    var __typename: String,
    var publicId: String,
    var resourceType: String,
    var type: String
)

data class Platform(
    var __typename: String,
    var label: String,
    var code: String
)

data class PlayMode(
    var __typename: String,
    var code: String,
    var label: String
)

data class ProductGallery(
    var __typename: String,
    var publicId: String,
    var resourceType: String,
    var type: String
)

data class Prices(
    var __typename: String,
    var minimum: Minimum,
    var maximum: Minimum?
)

data class TopLevelCategory(
    var __typename: String,
    var code: String,
    var label: String
)

data class Minimum(
    var __typename: String,
    var amountOff: Double,
    var currency: String,
    var discounted: Boolean,
    var finalPrice: Double,
    var regularPrice: Double
)

data class ProductX(
    var name: String,
    var sku: String,
    var nsuid: String
)

data class Offers(
    @SerializedName("@type")
    var type: String,
    var url: String,
    var priceCurrency: String,
    var price: String,
    var availability: String,
    var itemCondition: String
)