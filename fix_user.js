db.users.updateOne(
  { userName: "shikshak" },
  { $set: { roles: ["USER", "ADMIN"] } }
);
