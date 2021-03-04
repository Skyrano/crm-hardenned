<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">

<%@ include file="header.jsp" %>

<body class="page_body">

<%@ include file="navbar.jsp" %>

<main role="main" class="container">
    <div>
        <!-- simple paragraphe présentant notre application -->
        <h1>A Propos</h1>
        <!-- paragraphe contenant le lien vers le site de l'API -->
        <p>
           Ce projet a été fait dans le cadre du semestre 3 des élèves ingénieurs en cybersécurité du logiciel à l'ENSIBS. Ce projet a été encadré par Monsieur Salah SADOU et réalisé par Alexis MATIAS GOMES, Yoann KERGOSIEN, Lucas MANGIN et Alistair RAMEAU.
        </p>
    </div>
    <div>
        <h2>Nous contacter</h2>
        <form>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="nom">Nom</label>
                    <input type="text" class="form-control" id="nom" placeholder="Nom">
                </div>
                <div class="form-group col-md-6">
                    <label for="Prenom">Prénom</label>
                    <input type="text" class="form-control" id="Prenom" placeholder="Prénom">
                </div>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" class="form-control" id="email" placeholder="Email">
            </div>
            <div class="form-group">
                <label for="sujet">Sujet</label>
                <input type="text" class="form-control" id="sujet" placeholder="Sujet">
            </div>

            <div class="form-group">
                <label for="message">Message</label>
                <textarea class="form-control" id="message" rows="6"></textarea>
            </div>

            <button type="envoyer" class="btn btn-ensibs mb-4">Envoyer</button>

        </form>
    </div>

</main>



<%@ include file="footer.jsp" %>
</body>

</html>
