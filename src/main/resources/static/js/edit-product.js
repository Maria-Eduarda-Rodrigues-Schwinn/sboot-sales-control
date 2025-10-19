function getAuthHeaders() {
    return {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + localStorage.getItem("token")
    };
}

let products = [];

async function loadAllProducts() {
    try {
        const response = await fetch("/products", { headers: getAuthHeaders() });
        if (!response.ok) {
            const error = await response.json();
            alert("Erro: " + (error.error || JSON.stringify(error)));
            throw new Error("Erro ao carregar produtos: " + error);
        }
        products = await response.json();
        renderProducts(products);
    } catch (err) {
        alert(err.message);
        console.error(err);
    }
}

function renderProducts(list) {
    const tbody = document.querySelector("#productsTable tbody");
    tbody.innerHTML = "";
    list.forEach(p => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${p.id}</td>
            <td>${p.name}</td>
            <td>${p.category}</td>
            <td>${Number(p.unitPrice || 0).toFixed(2)}</td>
            <td>${p.unitOfMeasure}</td>
            <td>${p.quantity}</td>
        `;
        tr.addEventListener("click", () => {
            document.querySelectorAll("#productsTable tr").forEach(row => row.classList.remove("selected"));
            tr.classList.add("selected");
        });
        tbody.appendChild(tr);
    });
}

document.getElementById("btnSearch").addEventListener("click", async () => {
    const term = prompt("Digite o nome do produto para buscar:");
    if (!term) return;

    try {
        const response = await fetch(`/products/search?name=${encodeURIComponent(term)}`, {
            headers: getAuthHeaders()
        });
        if (!response.ok) {
            const error = await response.json();
            alert("Erro: " + (error.error || JSON.stringify(error)));
            throw new Error("Erro ao buscar produtos: " + error);
        }
        const result = await response.json();
        renderProducts(result);
    } catch (err) {
        alert(err.message);
        console.error(err);
    }
});

document.getElementById("btnLoadAll").addEventListener("click", loadAllProducts);

document.getElementById("btnEdit").addEventListener("click", async () => {
    const selected = document.querySelector("#productsTable tr.selected");
    if (!selected) {
        alert("Selecione um produto para editar.");
        return;
    }

    const id = parseInt(selected.cells[0].textContent);
    const product = products.find(p => p.id === id);

    const newPrice = prompt("Novo preço unitário:", product.unitPrice);
    const newQty = prompt("Nova quantidade:", product.quantity);

    if (newPrice === null || newQty === null) return;

    const update = {
        unitPrice: parseFloat(newPrice),
        quantity: parseInt(newQty)
    };

    try {
        const response = await fetch(`/products/${id}`, {
            method: "PUT",
            headers: getAuthHeaders(),
            body: JSON.stringify(update)
        });
        if (!response.ok) {
            const error = await response.json();
            alert("Erro: " + (error.error || JSON.stringify(error)));
            throw new Error("Erro ao atualizar produto: " + error);
        }

        alert("Produto atualizado com sucesso!");
        loadAllProducts();
    } catch (err) {
        alert(err.message);
        console.error(err);
    }
});

document.getElementById("btnDelete").addEventListener("click", async () => {
    const selected = document.querySelector("#productsTable tr.selected");
    if (!selected) {
        alert("Selecione um produto para excluir.");
        return;
    }

    const id = parseInt(selected.cells[0].textContent);
    if (!confirm("Tem certeza que deseja excluir o produto?")) return;

    try {
        const response = await fetch(`/products/${id}`, {
            method: "DELETE",
            headers: getAuthHeaders()
        });
        if (!response.ok) {
            const error = await response.json();
            alert("Erro: " + (error.error || JSON.stringify(error)));
            throw new Error("Erro ao excluir produto: " + error);
        }

        alert("Produto excluído com sucesso!");
        loadAllProducts();
    } catch (err) {
        alert(err.message);
        console.error(err);
    }
});

document.getElementById("menuLeave").addEventListener("click", () => {
    if (confirm("Deseja sair?")) {
        localStorage.clear();
        window.location.href = "../login.html";
    }
});

const role = localStorage.getItem("role");
if (role === "EMPLOYEE") {
    document.getElementById("menuRegisterProduct").style.pointerEvents = "none";
    document.getElementById("menuRegisterProduct").style.opacity = "0.5";
    document.getElementById("menuSalesReport").style.pointerEvents = "none";
    document.getElementById("menuSalesReport").style.opacity = "0.5";
}

loadAllProducts();
