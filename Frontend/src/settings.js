const URLs = {
    "Home": "/",
    "Login": "/login",
    "Products": "/products",
    "ProductId": "/poroduct/:id",
    "Request": "/request",
    "Data": "/data",
    "SimpleMovieData": "/movies",
    "Contact": "/contact",
    "About": "/about",
    "FAQ": "/faq",
    "Basket": "/cart",
    "Checkout": "/checkout",
    "NoMatch": "*"
}

function URLSettings() {
    const getURL = (key) => { return URLs[key] }

    return {
        getURL
    }
}
export default URLSettings();


