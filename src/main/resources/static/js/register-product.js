function getAuthHeaders() {
    return {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + localStorage.getItem("token")
    };
}

async function loadCategoriesAndUnits() {
    try {
        const [catRes, unitRes] = await Promise.all([
            fetch("/products/categories", { headers: getAuthHeaders() }),
            fetch("/products/units", { headers: getAuthHeaders() })
        ]);

        if (!catRes.ok || !unitRes.ok) {
            throw new Error("Erro ao buscar categorias ou unidades.");
        }

        const categories = await catRes.json();
        const units = await unitRes.json();

        const categorySelect = document.getElementById("category");
        categorySelect.innerHTML = '<option value="">Selecione</option>';
        categories.forEach(cat => {
            const opt = document.createElement("option");
            opt.value = cat;
            opt.textContent = cat;
            categorySelect.appendChild(opt);
        });

        const unitSelect = document.getElementById("unitOfMeasure");
        unitSelect.innerHTML = '<option value="">Selecione</option>';
        units.forEach(unit => {
            const opt = document.createElement("option");
            opt.value = unit;
            opt.textContent = unit;
            unitSelect.appendChild(opt);
        });
    } catch (err) {
        alert("Erro ao carregar categorias e unidades.");
        console.error(err);
    }
}

document.getElementById("btnClear").addEventListener("click", () => {
    document.getElementById("productForm").reset();
});

document.getElementById("productForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const name = document.getElementById("name").value.trim();
    const quantity = parseInt(document.getElementById("quantity").value.trim());
    const unitPrice = parseFloat(document.getElementById("unitPrice").value.trim());
    const category = document.getElementById("category").value;
    const unit = document.getElementById("unitOfMeasure").value;

    if (!name || isNaN(quantity) || quantity <= 0 || isNaN(unitPrice) || unitPrice <= 0 || !category || !unit) {
        alert("Preencha todos os campos corretamente!");
        return;
    }

    const product = {
        name,
        category,
        unitOfMeasure: unit,
        unitPrice,
        quantity
    };

    try {
        const response = await fetch("/products", {
            method: "POST",
            headers: getAuthHeaders(),
            body: JSON.stringify(product)
        });

        if (!response.ok) {
            const error = await response.text();
            alert("Erro ao cadastrar produto: " + error);
            return;
        }

        alert("Produto cadastrado com sucesso!");
        document.getElementById("productForm").reset();
    } catch (err) {
        alert("Erro de conexão com o servidor.");
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
    document.getElementById("menuEditProduct").style.pointerEvents = "none";
    document.getElementById("menuEditProduct").style.opacity = "0.5";
    document.getElementById("menuSalesReport").style.pointerEvents = "none";
    document.getElementById("menuSalesReport").style.opacity = "0.5";
}

loadCategoriesAndUnits();
