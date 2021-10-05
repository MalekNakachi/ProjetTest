import { Menu } from './menu.model';
// export let verticalMenuItems = [];
export let horizontalMenuItems = [];
// export const verticalMenuItems = [
//     new Menu (1, 'Dashboard', '/pages/dashboard', null, 'tachometer', null, false, 0),
//     new Menu (2, 'Dossiers', null, null, 'users', null, true, 0),
//   //  new Menu (21, 'Client par date', '/pages/Client', null, 'calendar-check-o', null, false, 2),
//     new Menu (3, 'Dossier Client', '/pages/Client', null, 'user', null, false, 2),
//
//     new Menu (21, 'Dossier Credit', '/pages/Client', null, 'credit-card', null, false, 2),
//
//     new Menu (22, 'Dossier Contentieux', '/pages/ClientParType', null, 'bolt', null, false, 2),
//     // new Menu (23, 'Client par montant', '/pages/Client', null, 'usd', null, false, 2),
//     // new Menu (4, 'Credit', null, null, 'credit-card', null, true, 0),
//     // new Menu (41, 'Credit par date', '/pages/Credit', null, 'calendar-o', null, false, 4),
//     // new Menu (42, 'Credit par type', '/pages/Credit', null, 'bolt', null, false, 4),
//     // new Menu (43, 'Credit par montant', '/pages/Credit', null, 'usd', null, false, 4),
//     // new Menu (3, 'Statistique des Client', '/pages/dashboard', null, 'pie-chart', null, false, 0),
//     // new Menu (5, 'Statistique des Credit', '/pages/dashboard', null, 'pie-chart', null, false, 0),
//     // new Menu (50, 'Calendrier', '/pages/calendar', null, 'calendar', null, false, 0),
//     // new Menu (16, 'Courrier', '/pages/mailbox', null, 'envelope-o', null, false, 0),
//     // new Menu (16, 'Profile', null, null, 'user-circle-o', null, false, 0),
//     new Menu (16, 'Administrateur', null, null, 'gear', null, false, 0)
// ];
//
// export const horizontalMenuItems = [
//     new Menu (1, 'Dashboard', '/pages/dashboard', null, 'tachometer', null, false, 0),
//     new Menu (2, 'Dossiers', null, null, 'users', null, true, 0),
//     //  new Menu (21, 'Client par date', '/pages/Client', null, 'calendar-check-o', null, false, 2),
//     new Menu (3, 'Dossier Client', '/pages/Client', null, 'user', null, false, 2),
//
//     new Menu (21, 'Dossier Credit', '/pages/Client', null, 'credit-card', null, false, 2),
//
//     new Menu (22, 'Dossier Contentieux', '/pages/ClientParType', null, 'bolt', null, false, 2),
//     // new Menu (23, 'Client par montant', '/pages/Client', null, 'usd', null, false, 2),
//     // new Menu (4, 'Credit', null, null, 'credit-card', null, true, 0),
//     // new Menu (41, 'Credit par date', '/pages/Credit', null, 'calendar-o', null, false, 4),
//     // new Menu (42, 'Credit par type', '/pages/Credit', null, 'bolt', null, false, 4),
//     // new Menu (43, 'Credit par montant', '/pages/Credit', null, 'usd', null, false, 4),
//     // new Menu (3, 'Statistique des Client', '/pages/dashboard', null, 'pie-chart', null, false, 0),
//     // new Menu (5, 'Statistique des Credit', '/pages/dashboard', null, 'pie-chart', null, false, 0),
//     // new Menu (50, 'Calendrier', '/pages/calendar', null, 'calendar', null, false, 0),
//     // new Menu (16, 'Courrier', '/pages/mailbox', null, 'envelope-o', null, false, 0),
//     // new Menu (16, 'Profile', null, null, 'user-circle-o', null, false, 0),
//     new Menu (16, 'Administrateur', null, null, 'gear', null, false, 0)
// ];

export const profilDemandeur=[
  new Menu (1, 'Tableau de Bord', '/pages/dashboard', null, 'tachometer', null, false, 0),


  new Menu (14, 'Gestion des demandes Achats  ', null, null, 'folder', null, true, 0),
  new Menu (15, 'Création demande Achat', '/pages/demande', null, 'user', null, false, 14),
  new Menu (16, "Consultation des demandes Achats", '/pages/demandes', null, 'user', null, false, 14),


]


export const profilDirectionAchat=[
    new Menu (1, 'Tableau de Bord', '/pages/dashboard', null, 'tachometer', null, false, 0),

    new Menu (2, 'Gestion des Articles  ', null, null, 'folder', null, true, 0),
    new Menu (3, 'Création Article', '/pages/article', null, 'user', null, false, 2),
    new Menu (4, 'Consultation des Articles', '/pages/articles', null, 'user', null, false, 2),

    new Menu (5, 'Gestion des Fournisseurs  ', null, null, 'folder', null, true, 0),
    new Menu (6, 'Création Fournisseur', '/pages/fournisseur', null, 'user', null, false, 5),
    new Menu (7, 'Consultation des Fournisseurs', '/pages/fournisseurs', null, 'user', null, false, 5),

    new Menu (8, 'Gestion des Offres  ', null, null, 'folder', null, true, 0),
    new Menu (9, 'Création Offre', '/pages/offre', null, 'user', null, false, 8),
    new Menu (10, 'Consultation des Offres', '/pages/offres', null, 'user', null, false, 8),

    new Menu (11, 'Gestion des Pv  ', null, null, 'folder', null, true, 0),
    new Menu (12, 'Création Pv', '/pages/pv', null, 'user', null, false, 11),
    new Menu (13, "Consultation des Pv's", '/pages/pvs', null, 'user', null, false, 11),

    new Menu (14, 'Gestion des demandes Achats  ', null, null, 'folder', null, true, 0),
    new Menu (15, 'Création demande Achat', '/pages/demande', null, 'user', null, false, 14),
    new Menu (16, "Consultation des demandes Achats", '/pages/demandes', null, 'user', null, false, 14),

    new Menu (17, 'Gestion des Bon Commande  ', null, null, 'folder', null, true, 0),
    new Menu (18, 'Création Bon Commande', '/pages/boncommande', null, 'user', null, false, 17),
    new Menu (19, "Consultation des Bon Commandes", '/pages/boncommandes', null, 'user', null, false, 17),
]


export const profilClient=[
    new Menu (1, 'Tableau de Bord', '/pages/dashboard', null, 'tachometer', null, false, 0),

    new Menu (2, 'Gestion des Articles  ', null, null, 'folder', null, true, 0),
    new Menu (3, 'Création Article', '/pages/article', null, 'user', null, false, 2),
    new Menu (4, 'Consultation des Articles', '/pages/articles', null, 'user', null, false, 2),

    new Menu (5, 'Gestion des Fournisseurs  ', null, null, 'folder', null, true, 0),
    new Menu (6, 'Création Fournisseur', '/pages/fournisseur', null, 'user', null, false, 5),
    new Menu (7, 'Consultation des Fournisseurs', '/pages/fournisseurs', null, 'user', null, false, 5),

    new Menu (8, 'Gestion des Offres  ', null, null, 'folder', null, true, 0),
    new Menu (9, 'Création Offre', '/pages/offre', null, 'user', null, false, 8),
    new Menu (10, 'Consultation des Offres', '/pages/offres', null, 'user', null, false, 8),

    new Menu (11, 'Gestion des Pv  ', null, null, 'folder', null, true, 0),
    new Menu (12, 'Création Pv', '/pages/pv', null, 'user', null, false, 11),
    new Menu (13, "Consultation des Pv's", '/pages/pvs', null, 'user', null, false, 11),

    new Menu (14, 'Gestion des demandes Achats  ', null, null, 'folder', null, true, 0),
    new Menu (15, 'Création demande Achat', '/pages/demande', null, 'user', null, false, 14),
    new Menu (16, "Consultation des demandes Achats", '/pages/demandes', null, 'user', null, false, 14),

    new Menu (17, 'Gestion des Bon Commande  ', null, null, 'folder', null, true, 0),
    new Menu (18, 'Création Bon Commande', '/pages/boncommande', null, 'user', null, false, 17),
    new Menu (19, "Consultation des Bon Commandes", '/pages/boncommandes', null, 'user', null, false, 17),
]


export const profilFinancier=[
    new Menu (1, 'Tableau de Bord', '/pages/dashboard', null, 'tachometer', null, false, 0),

    new Menu (14, 'Gestion des demandes Achats  ', null, null, 'folder', null, true, 0),
    new Menu (15, 'Création demande Achat', '/pages/demande', null, 'user', null, false, 14),
    new Menu (16, "Consultation des demandes Achats", '/pages/demandes', null, 'user', null, false, 14),


    new Menu (11, 'Gestion des Pv  ', null, null, 'folder', null, true, 0),
    new Menu (12, 'Création Pv', '/pages/pv', null, 'user', null, false, 11),
    new Menu (13, "Consultation des Pv's", '/pages/pvs', null, 'user', null, false, 11),

    new Menu (17, 'Gestion des Bon Commande  ', null, null, 'folder', null, true, 0),
    new Menu (18, 'Création Bon Commande', '/pages/boncommande', null, 'user', null, false, 17),
    new Menu (19, "Consultation des Bon Commandes", '/pages/boncommandes', null, 'user', null, false, 17),
]


export const profilControlleur=[
    new Menu (1, 'Tableau de Bord', '/pages/dashboard', null, 'tachometer', null, false, 0),

    new Menu (2, 'Gestion des Articles  ', null, null, 'folder', null, true, 0),
    new Menu (3, 'Création Article', '/pages/article', null, 'user', null, false, 2),
    new Menu (4, 'Consultation des Articles', '/pages/articles', null, 'user', null, false, 2),

    new Menu (5, 'Gestion des Fournisseurs  ', null, null, 'folder', null, true, 0),
    new Menu (6, 'Création Fournisseur', '/pages/fournisseur', null, 'user', null, false, 5),
    new Menu (7, 'Consultation des Fournisseurs', '/pages/fournisseurs', null, 'user', null, false, 5),

    new Menu (8, 'Gestion des Offres  ', null, null, 'folder', null, true, 0),
    new Menu (9, 'Création Offre', '/pages/offre', null, 'user', null, false, 8),
    new Menu (10, 'Consultation des Offres', '/pages/offres', null, 'user', null, false, 8),

    new Menu (11, 'Gestion des Pv  ', null, null, 'folder', null, true, 0),
    new Menu (12, 'Création Pv', '/pages/pv', null, 'user', null, false, 11),
    new Menu (13, "Consultation des Pv's", '/pages/pvs', null, 'user', null, false, 11),

    new Menu (14, 'Gestion des demandes Achats  ', null, null, 'folder', null, true, 0),
    new Menu (15, 'Création demande Achat', '/pages/demande', null, 'user', null, false, 14),
    new Menu (16, "Consultation des demandes Achats", '/pages/demandes', null, 'user', null, false, 14),

    new Menu (17, 'Gestion des Bon Commande  ', null, null, 'folder', null, true, 0),
    new Menu (18, 'Création Bon Commande', '/pages/boncommande', null, 'user', null, false, 17),
    new Menu (19, "Consultation des Bon Commandes", '/pages/boncommandes', null, 'user', null, false, 17),
]

export const profileRoot=[
    new Menu (1, 'Tableau de Bord', '/pages/dashboard', null, 'tachometer', null, false, 0),

    new Menu (2, 'Gestion des Articles  ', null, null, 'folder', null, true, 0),
    new Menu (3, 'Création Article', '/pages/article', null, 'user', null, false, 2),
    new Menu (4, 'Consultation des Articles', '/pages/articles', null, 'user', null, false, 2),

    new Menu (5, 'Gestion des Fournisseurs  ', null, null, 'folder', null, true, 0),
    new Menu (6, 'Création Fournisseur', '/pages/fournisseur', null, 'user', null, false, 5),
    new Menu (7, 'Consultation des Fournisseurs', '/pages/fournisseurs', null, 'user', null, false, 5),

    new Menu (8, 'Gestion des Offres  ', null, null, 'folder', null, true, 0),
    new Menu (9, 'Création Offre', '/pages/offre', null, 'user', null, false, 8),
    new Menu (10, 'Consultation des Offres', '/pages/offres', null, 'user', null, false, 8),

    new Menu (11, 'Gestion des Pv  ', null, null, 'folder', null, true, 0),
    new Menu (12, 'Création Pv', '/pages/pv', null, 'user', null, false, 11),
    new Menu (13, "Consultation des Pv's", '/pages/pvs', null, 'user', null, false, 11),

    new Menu (14, 'Gestion des demandes Achats  ', null, null, 'folder', null, true, 0),
    new Menu (15, 'Création demande Achat', '/pages/demande', null, 'user', null, false, 14),
    new Menu (16, "Consultation des demandes Achats", '/pages/demandes', null, 'user', null, false, 14),

    new Menu (17, 'Gestion des Bon Commande  ', null, null, 'folder', null, true, 0),
    new Menu (18, 'Création Bon Commande', '/pages/boncommande', null, 'user', null, false, 17),
    new Menu (19, "Consultation des Bon Commandes", '/pages/boncommandes', null, 'user', null, false, 17),
]
export const profilAgent=[
  new Menu (1, 'Tableau de Bord', '/pages/dashboard', null, 'tachometer', null, false, 0),
  new Menu (2, 'Gestion Demandes Crédits  ', null, null, 'folder', null, true, 0),
  new Menu (3, 'Créer Demande Crédit', '/pages/FormulaireDemandeCredit', null, 'user', null, false, 2),
  new Menu (4, 'Consulter Demandes Crédits', '/pages/credits', null, 'user', null, false, 2),
]
export const profilAdministareur=[
    new Menu (1, 'Tableau de Bord', '/pages/dashboard', null, 'tachometer', null, false, 0),
    new Menu (2, 'Gestion Demandes Crédits  ', null, null, 'folder', null, true, 0),
    new Menu (3, 'Créer Demande Crédit', '/pages/FormulaireDemandeCredit', null, 'user', null, false, 2),
    new Menu (4, 'Consulter Demandes Crédits', '/pages/credits', null, 'user', null, false, 2),
  new Menu (5, 'Administration      ', null, null, 'folder', null, true, 0),
  new Menu (6, 'Gestion Utilisateurs', '/pages/utilisateurs', null, 'user', null, false, 5),
  new Menu (7, 'Gestion Roles', '/pages/roles', null, 'role', null, false, 5),

]
horizontalMenuItems=profilAdministareur;
// verticalMenuItems=profilAdministareur;
