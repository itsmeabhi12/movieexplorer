export function ErrorView({ message }: { message?: string }) {
  return (
    <div className="display-flex align-centre justify-centre">
      {message ?? "Something went wrong.."}
    </div>
  );
}
